package advice_natio.project.com.getmoney.Activites;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.Constants;
import advice_natio.project.com.getmoney.BaseActivity.DialogUtils;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.PostApi;
import advice_natio.project.com.getmoney.R;

import static advice_natio.project.com.getmoney.BaseActivity.Constants.REQUEST_OPEN_CAMERA;


/**
 * Created by Surya Chundawat on 8/17/2017.
 */

public class BankDetails extends BaseActivity implements View.OnClickListener,ApiResponse
{
    private AppCompatButton btnsubmit;
    private Context context;
    private ProgressBar progressBar;
    private EditText edtpayeename,edtbankname,edtbankbanch,edtifsccode,edtmicrnumber,edtaccountnumber;
    private String TAG="BankDetails";
    private PostApi postApi;
    private String strname,strbankname,strbankbranch,strifsccode,strmicrnumber,straccountnumber,stririd;
    private String imagePath="";
    private int isGalleryOpen = -1;
    private ImageView Upload_img;
    private LinearLayout linAdd;
    private MultipartEntity entity;
    private File photoFile;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankdetails);
        setupExplodeWindowAnimations(Gravity.BOTTOM);
       //InitiStatus();
       //InitiToolbar((Toolbar)findViewById(R.id.toolbar),getResources().getString(R.string.titile_bankdetails));
        Iniview();
        context= this;
    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,BankDetails.class));
    }

    private void Iniview()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        stririd = sharedPreferences.getString("irid","");
        btnsubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
        progressBar  = (ProgressBar ) findViewById(R.id.progressBar);
        edtpayeename = (EditText) findViewById(R.id.edtpayeename);
        edtbankname = (EditText) findViewById(R.id.edtbankname);
        edtbankbanch= (EditText) findViewById(R.id.edtbankbanch);
        edtifsccode = (EditText) findViewById(R.id.edtifsccode);
        edtmicrnumber = (EditText) findViewById(R.id.edtmicrnumber);
        edtaccountnumber =(EditText) findViewById(R.id.edtaccountnumber);
        Upload_img = (ImageView) findViewById(R.id.addevent_upload_img);
        linAdd = (LinearLayout) findViewById(R.id.addevent_linAdd);
        linAdd.setVisibility(View.VISIBLE);
        btnsubmit.setOnClickListener(this);
    }



    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void submit()
    {
        strname = edtpayeename.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strbankname = edtbankname.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strbankbranch = edtbankbanch.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strifsccode = edtifsccode.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strmicrnumber = edtmicrnumber.getText().toString().replaceAll("\\s{2,}", " ").trim();
        straccountnumber = edtaccountnumber.getText().toString().replaceAll("\\s{2,}", " ").trim();
        if (strname == null || strname.equals("null")|| strname.isEmpty()) {
            showSnackbar(edtpayeename,"Enter Bank Name");
        }else if (strbankname == null || strbankname.equals("null")|| strbankname.isEmpty()) {
            showSnackbar(edtpayeename,"Enter Enter BankName");
        }else if (strbankbranch == null || strbankbranch.equals("null")|| strbankbranch.isEmpty()) {
            showSnackbar(edtpayeename,"Enter Branch Name");
        }else if (strifsccode == null || strifsccode.equals("null")|| strifsccode.isEmpty()) {
            showSnackbar(edtpayeename,"Enter IFSC Code");
        }else if (strmicrnumber == null || strmicrnumber.equals("null")|| strmicrnumber.isEmpty()) {
            showSnackbar(edtpayeename,"Enter MICR Number");
        }else if (straccountnumber == null || straccountnumber.equals("null")|| straccountnumber.isEmpty()) {
            showSnackbar(edtpayeename,"Enter Account Number");
        } else {
            if (isOnline(context)) {
                callback(3);
            } else {showSnackbar(edtpayeename,"Oops!! Please Check Network");}
        }
    }


    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.btnSubmit:
                submit();
                break;
        }
    }

    private void callback(int id, String...data) {


        entity = new MultipartEntity();
        //http://ec2-13-126-97-168.ap-south-1.compute.amazonaws.com:8080/AdviseNation/api/users/17041409/productSubCategory/1/product?productName=test&productDescription=stet&productFeatures=good&productPrice=400
        String URL = NetworkUrl.URL_SETBANKDETAILS;
        Log.e(TAG, "callback: "+URL );
        MultipartRequest multipartRequest = new MultipartRequest(URL, "", null, photoFile, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialogStop();
                Log.e(TAG, "onResponse: " + response.statusCode);
                //showSnackbarSuccess(viewpart,"Submission data successfully...");
                showSnackbar(edtpayeename,"Submission data successfully...");
                edtpayeename.getText().clear();
                edtbankname.getText().clear();
                edtbankbanch.getText().clear();
                edtifsccode.getText().clear();
                edtmicrnumber.getText().clear();
                edtaccountnumber.getText().clear();
                //product_price.clearFocus();
                //product_name.setFocusableInTouchMode(true);
                linAdd.setVisibility(View.VISIBLE);
                Glide.with(context).load(R.mipmap.ic_placeholder).into(Upload_img);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //409-Unsupported image format with given content-type
                //400-412(image)-"Missing input parameters
                progressDialogStop();
                Log.e(TAG, "onErrorResponse: " + volleyError.networkResponse.statusCode);
                showSnackbar(edtpayeename,"Submission data failed");
                //Toast.makeText(MainActivity.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                ;
            }
        }) {
        };

        RequestQueue rQueue = Volley.newRequestQueue(this);
        rQueue.add(multipartRequest);



       /* switch (id) {
            case 3:
                String URL = NetworkUrl.URL_PERSONAL;
                String apiTag = NetworkUrl.URL_PERSONAL;
                JSONObject jsonObject = GetLoginObject();
                Log.e(TAG, "callback: json" + jsonObject.toString());
                postApi = new PostApi(context, URL, jsonObject, apiTag, TAG ,3);  // 1 is id for call deshboard api
                break;

            default:
                break;
        }*/
    }

    private JSONObject GetLoginObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_NomineeName" : "Geet Panda","IR_Relation" : "W",
        // "IR_DOB" : "30-Sep-2017","IR_PANNo" : "AGL7380A","IR_AadhaarNo" : "123456789012"}]}
        //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_PayeeName" : "myname","IR_BankName" : "sdajsdkad",
        // "IR_BankBranch" : "ncbscbksj","IR_AccountNo" : "3298379822","IR_IFSCCode" : "22323232",
        // "IR_MicrNo" : "12344566","IR_ChequeImage_Path" : "17330001_Bank_ChequeImage.jpg",
        // "IR_Bank_ChequeImage" : "kvsknsknsncknsckldnhow3y49823748923dhsjakbcjasdjhsajakbxdas"}]}


        try {
            jsonObject1.put("IR_ID", "17330001");
            jsonObject1.put("IR_PayeeName",strname);
            jsonObject1.put("IR_BankName",strbankname);
            jsonObject1.put("IR_BankBranch",strbankbranch);
            jsonObject1.put("IR_AccountNo",strmicrnumber);
            jsonObject1.put("IR_IFSCCode",strifsccode);
            jsonObject1.put("IR_ChequeImage_Path","17330001_Bank_ChequeImage.jpg");
            jsonObject1.put("IR_Bank_ChequeImage","kvsknsknsncknsckldnhow3y49823748923dhsjakbcjasdjhsajakbxdas");
            jsonArray.put(jsonObject1);
            jsonObject.put("jsonObject_Details",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "GetLoginObject: " + e.getMessage());
        }
        Log.d(TAG,"Registration Object"+jsonObject);
        return jsonObject;
    }


    private void showProgress(boolean b) {
        if (b) {
            AnimationUtils.animateScaleOut(progressBar);
        } else {
            AnimationUtils.animateScaleIn(progressBar);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                Products.StartActivity(context);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnSucess(JSONObject response, int id) {

    }

    @Override
    public void OnFailed(int error, int id) {

    }


    public void onClickNewsPhoto(View view){

        final Dialog dialog = new DialogUtils(this).setupCustomeDialogFromBottom(R.layout.dialog_gallery);
        ImageView imgCamera = (ImageView) dialog.findViewById(R.id.imgCamera);
        ImageView imgGallery = (ImageView) dialog.findViewById(R.id.imgGallery);
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openCamera();
            }
        });
        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openGallery();
            }
        });
        dialog.show();
    }

    private void openCamera()
    {
        Log.e(TAG, "openCamera: " );
        isGalleryOpen = 1;
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,this) &&
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, this) && checkPermission(Manifest.permission.CAMERA, this)) {

            Log.e(TAG, "checkPermission: true" );
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_OPEN_CAMERA);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "catch openCamera: "+e.getMessage() );
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e(TAG, "checkPermission: false" );

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.REQUEST_PERMISSION_WRITE_STORAGE);
            }
        }

    }

    private void openGallery() {
        isGalleryOpen = 0;
        Log.e(TAG, "openGallery: " );


        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,this) &&
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, this) && checkPermission(Manifest.permission.CAMERA, this)) {

            try {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constants.REQUEST_OPEN_GALLERY);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "catch openGallery: "+e.getMessage() );
            }

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.REQUEST_PERMISSION_WRITE_STORAGE);
            }
        }

    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Advice_" + timeStamp + "_";
        File sdCard = new File(Environment.getExternalStorageDirectory()+"/AdviceNation/Images");
        if(!sdCard.exists())
            sdCard.mkdirs();
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    sdCard      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.REQUEST_PERMISSION_WRITE_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(isGalleryOpen == 0)
                    openGallery();
                else if(isGalleryOpen == 1)
                    openCamera();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_OPEN_CAMERA) {
                getCamaraImageUri(data);


            } else if (requestCode == Constants.REQUEST_OPEN_GALLERY) {
                getGalleryImageUri(data);
            }
        }
    }

    private Uri getGalleryImageUri(Intent data) {
        Uri uri = null;
        try {
            Uri imageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(imageUri, projection, null, null,
                    null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);
            imagePath = selectedImagePath;
            File file = new File(imagePath);
            photoFile = file;
            uri= Uri.fromFile(new File(imagePath));
            Log.e(TAG, "Image Gallery" + imagePath);
            //bitmap = galleryCameraDialog.decodeUri(imageUri);
            displayImage(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }
    private Uri getCamaraImageUri(Intent data) {
        Uri uri = null;
        try {
            // if you use intent put extra  output then you not necessary to use output stream.
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            photoFile = createImageFile();
            imagePath = photoFile.getAbsolutePath();
            Log.i(TAG, "getCamaraImageUri: " + imagePath);
            uri= Uri.fromFile(new File(imagePath));

            FileOutputStream fo = new FileOutputStream(photoFile);
            fo.write(bytes.toByteArray());
            fo.close();

            displayImage(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

    private void displayImage(Uri photoUri) {
        linAdd.setVisibility(View.GONE);
        Glide.with(this).load(photoUri).into(Upload_img);
    }

    public void onClickSubmit(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    class MultipartRequest extends Request<NetworkResponse> {
        private final Response.Listener<NetworkResponse> mListener;
        private final Response.ErrorListener mErrorListener;
        private final Map<String, String> mHeaders;
        private final String bearerToken;
        ArrayList<String> img_path1;
        private File mfile;



        public MultipartRequest(String url, String bearerToken, Map<String, String> headers, File file, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
            super(Method.POST, url, errorListener);
            this.mListener = listener;
            this.mErrorListener = errorListener;
            this.bearerToken = bearerToken;
            this.mHeaders = headers;
            this.mfile = file;
            //buildMultipartEntity(file);

        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            // headers.put("Content-Type", "text/html");
            headers.put("INPUT",GetLoginObjectN().toString());
            headers.put("TimeOut","5");
            headers.put("Content-Type", "application/json;charset=utf-8");
            headers.put("Content-Length", "0");
            return headers;
        }

        private JSONObject GetLoginObjectN() {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_NomineeName" : "Geet Panda","IR_Relation" : "W",
            // "IR_DOB" : "30-Sep-2017","IR_PANNo" : "AGL7380A","IR_AadhaarNo" : "123456789012"}]}
            //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_PayeeName" : "myname","IR_BankName" : "sdajsdkad",
            // "IR_BankBranch" : "ncbscbksj","IR_AccountNo" : "3298379822","IR_IFSCCode" : "22323232",
            // "IR_MicrNo" : "12344566","IR_ChequeImage_Path" : "17330001_Bank_ChequeImage.jpg",
            // "IR_Bank_ChequeImage" : "kvsknsknsncknsckldnhow3y49823748923dhsjakbcjasdjhsajakbxdas"}]}


            try {
                jsonObject1.put("IR_ID", stririd);
                jsonObject1.put("IR_PayeeName",strname);
                jsonObject1.put("IR_BankName",strbankname);
                jsonObject1.put("IR_BankBranch",strbankbranch);
                jsonObject1.put("IR_AccountNo",strmicrnumber);
                jsonObject1.put("IR_IFSCCode",strifsccode);
                jsonObject1.put("IR_ChequeImage_Path",stririd+"_Bank_ChequeImage.jpg");
                FileBody fBody = new FileBody(mfile, "image/jpg");
                jsonObject1.put("IR_Bank_ChequeImage", fBody);
                jsonArray.put(jsonObject1);
                jsonObject.put("jsonObject_Details",jsonArray);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "GetLoginObject: " + e.getMessage());
            }
            Log.d(TAG,"Registration Object"+jsonObject);
            return jsonObject;
        }


        private void buildMultipartEntity(File file) {
            //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_PayeeName" : "myname","IR_BankName" : "sdajsdkad",
            // "IR_BankBranch" : "ncbscbksj","IR_AccountNo" : "3298379822","IR_IFSCCode" : "22323232","IR_MicrNo" : "12344566",
            // "IR_ChequeImage_Path" : "17330001_Bank_ChequeImage.jpg",
            // "IR_Bank_ChequeImage" : "kvsknsknsncknsckldnhow3y49823748923dhsjakbcjasdjhsajakbxdas"}]}


            try {
                FileBody fBody = new FileBody(file, "image/jpg");
                /*entity.addPart("IR_ID", new StringBody(stririd + ""));
                entity.addPart("IR_PayeeName", new StringBody(strname.trim() + ""));
                entity.addPart("IR_BankName", new StringBody(strbankname.trim() + ""));
                entity.addPart("IR_BankBranch", new StringBody(strbankbranch.trim() + ""));
                entity.addPart("IR_AccountNo", new StringBody(straccountnumber.trim() + ""));
                entity.addPart("IR_IFSCCode", new StringBody(strifsccode.trim()+""));
                entity.addPart("IR_MicrNo", new StringBody(strmicrnumber.trim()+""));
                entity.addPart("IR_ChequeImage_Path", new StringBody(stririd.trim()+"_Bank_ChequeImage.jpg" +""));*/
                entity.addPart("IR_Bank_ChequeImage", fBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public String getBodyContentType() {
            return entity.getContentType().getValue();
        }

        @Override
        public byte[] getBody() throws AuthFailureError {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                entity.writeTo(bos);
            } catch (IOException e) {
                VolleyLog.e("IOException writing to ByteArrayOutputStream");
            }
            return bos.toByteArray();
        }

        @Override
        protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {

            try {
                return Response.success(
                        response,
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (Exception e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(NetworkResponse response) {

            Log.e("response ", " " + response.statusCode);
            if (response.statusCode == 200) {
                img_path1 = new ArrayList<String>();
            }
            mListener.onResponse(response);
        }


        @Override
        public void deliverError(VolleyError error) {

            mErrorListener.onErrorResponse(error);
        }
    }







}
