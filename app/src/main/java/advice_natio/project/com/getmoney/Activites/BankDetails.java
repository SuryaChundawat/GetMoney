package advice_natio.project.com.getmoney.Activites;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private EditText edtpayeename,edtbankname,edtbankbanch,edtifsccode,edtmicrnumber;
    private String TAG="BankDetails";
    private PostApi postApi;
    private String strname,strbankname,strbankbranch,strifsccode,strmicrnumber;
    private String imagePath="";
    private int isGalleryOpen = -1;
    private ImageView Upload_img;
    private LinearLayout linAdd;

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
        btnsubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
        progressBar  = (ProgressBar ) findViewById(R.id.progressBar);
        edtpayeename = (EditText) findViewById(R.id.edtpayeename);
        edtbankname = (EditText) findViewById(R.id.edtbankname);
        edtbankbanch= (EditText) findViewById(R.id.edtbankbanch);
        edtifsccode = (EditText) findViewById(R.id.edtifsccode);
        edtmicrnumber = (EditText) findViewById(R.id.edtmicrnumber);
        Upload_img = (ImageView) findViewById(R.id.addevent_upload_img);
        linAdd = (LinearLayout) findViewById(R.id.addevent_linAdd);
        linAdd.setVisibility(View.VISIBLE);
        btnsubmit.setOnClickListener(this);
    }



    private void submit()
    {
        strname = edtpayeename.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strbankname = edtbankname.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strbankbranch = edtbankbanch.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strifsccode = edtifsccode.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strmicrnumber = edtmicrnumber.getText().toString().replaceAll("\\s{2,}", " ").trim();
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
        }else {
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
        switch (id) {
            case 3:
                String URL = NetworkUrl.URL_PERSONAL;
                String apiTag = NetworkUrl.URL_PERSONAL;
                JSONObject jsonObject = GetLoginObject();
                Log.e(TAG, "callback: json" + jsonObject.toString());
                postApi = new PostApi(context, URL, jsonObject, apiTag, TAG ,3);  // 1 is id for call deshboard api
                break;

            default:
                break;
        }
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

            File photoFile = createImageFile();
            imagePath = photoFile.getAbsolutePath();
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



}
