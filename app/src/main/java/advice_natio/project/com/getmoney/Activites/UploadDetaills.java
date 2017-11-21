package advice_natio.project.com.getmoney.Activites;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import advice_natio.project.com.getmoney.Activites.Adapter.UploadAdapter;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.Constants;
import advice_natio.project.com.getmoney.BaseActivity.DialogUtils;
import advice_natio.project.com.getmoney.R;
import advice_natio.project.com.getmoney.RecylerViewClick.RecyclerItemClickListener;
import advice_natio.project.com.getmoney.ScanActivity.ScanActivity;

import static advice_natio.project.com.getmoney.BaseActivity.Constants.REQUEST_CODE;
import static advice_natio.project.com.getmoney.BaseActivity.Constants.REQUEST_OPEN_CAMERA;
import static advice_natio.project.com.getmoney.BaseActivity.Constants.SCANNED_RESULT;

/**
 * Created by Surya Chundawat on 8/19/2017.
 */

public class UploadDetaills extends BaseActivity implements View.OnClickListener
{
    /*private RecyclerView ListView;*/
    private Context context;
    private ArrayList<String> picname;
    private String TAG="UploadDetails";
    private AppCompatButton btnBuymore;
    private ImageView imgAdharcard,imgPancard;
    private EditText edtpannumber,edtaadharnumber;
    private LinearLayout linAddpan,linAddadhar;
    private String imagePath="";
    private int isGalleryOpen = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaddetails);
        InitiStatus();
        //InitiToolbar((Toolbar)findViewById(R.id.toolbar),getResources().getString(R.string.titile_uploaddeatails));
        context = this;
        //InitList();
        Init();
        //setView();
    }

    private void Init()
    {
        btnBuymore =(AppCompatButton) findViewById(R.id.btnSubmit);
        btnBuymore.setOnClickListener(this);
        edtpannumber =(EditText) findViewById(R.id.edtpannumber);
        edtaadharnumber = (EditText) findViewById(R.id.edtaadharnumber);
        imgAdharcard = (ImageView) findViewById(R.id.image_uploadpan);
        imgPancard  = (ImageView) findViewById(R.id.image_uploadpan);
        imgAdharcard.setOnClickListener(this);
        imgPancard.setOnClickListener(this);
        linAddpan = (LinearLayout) findViewById(R.id.upload_linAdd);
        linAddpan.setVisibility(View.VISIBLE);
        linAddadhar = (LinearLayout) findViewById(R.id.upload_linAddaadahr);
        linAddadhar.setVisibility(View.VISIBLE);

    }


    private void InitList() {
        picname = new ArrayList<>();
        picname.add("Aadhar Card");
        picname.add("Bank Cheque");
        picname.add("Your Pic");

    }


    public static void StartActivity(Context context)
    {
        context.startActivity(new Intent(context,UploadDetaills.class));

    }

    @Override
    public void onClick(View view) {
        int getID = view.getId();
        switch (getID)
        {
            case R.id.btnSubmit:
                Products.StartActivity(context);
                break;
            case R.id.image:
                Intent intent = new Intent(this, ScanActivity.class);
                intent.putExtra(Constants.OPEN_INTENT_PREFERENCE, R.id.image);
                startActivityForResult(intent, REQUEST_CODE);
                break;
           /* case R.id.imageBankCheque:
                Intent intentBankCheque = new Intent(this, ScanActivity.class);
                intentBankCheque.putExtra(Constants.OPEN_INTENT_PREFERENCE, R.id.imageBankCheque);
                startActivityForResult(intentBankCheque, REQUEST_CODE);
                break;
            case R.id.imageyourpic:
                Intent intentYourPic= new Intent(this, ScanActivity.class);
                intentYourPic.putExtra(Constants.OPEN_INTENT_PREFERENCE, R.id.imageyourpic);
                startActivityForResult(intentYourPic, REQUEST_CODE);
                break;*/
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



    //Camera Logic here

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
        linAddpan.setVisibility(View.GONE);
        Glide.with(this).load(photoUri).into(imgPancard);
    }


    public void onClickSubmit(View view){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }


    }
}
