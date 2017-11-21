package advice_natio.project.com.getmoney.Activites;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.Constants;
import advice_natio.project.com.getmoney.BaseActivity.DialogUtils;
import advice_natio.project.com.getmoney.Fragment.Personal;
import advice_natio.project.com.getmoney.Fragment.FoodFragment;
import advice_natio.project.com.getmoney.Fragment.FriendsFragment;
import advice_natio.project.com.getmoney.Fragment.NearbyFragment;
import advice_natio.project.com.getmoney.Fragment.OnFragmentInteractionListener;
import advice_natio.project.com.getmoney.Fragment.RecentsFragment;

import advice_natio.project.com.getmoney.R;
import advice_natio.project.com.getmoney.circleimageview.CircleImageView;
import advice_natio.project.com.getmoney.scrap.FiveColorChangingTabsActivity;

import static advice_natio.project.com.getmoney.BaseActivity.Constants.REQUEST_OPEN_CAMERA;

/**
 * Created by Surya Chundawat on 8/20/2017.
 */

public class Dashboard extends BaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener,OnFragmentInteractionListener {
    private static String strIRID;
    private Toolbar toolbar;
    private AppCompatButton btnEncash,btnBuymore;
    private Context context;
    private int isGalleryOpen = -1;
    private String imagePath = "";
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    private static final String TAG = "DashboardActivity";
    private static final String TAG_HOME = "Recents";
    private static final String TAG_FAV = "Favorites";
    private static final String TAG_NEAR = "Nearby";
    private static final String TAG_FRIENDS = "Friends";
    private static final String TAG_FOOD = "Food";
    public static String CURRENT_TAG = TAG_HOME;
    private LinearLayout linAdd;
    private ImageView yourpic;
    Class clazz = null;
    private CircleImageView circleImageView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Handler mHandler;
    private BottomNavigationView bottomBar;
    private static String stririd,strmobile,strname,strmsg;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        checkPermission();
        InitiStatus();
        InitiToolbar(toolbar,"");
        getView();
        clazz = FiveColorChangingTabsActivity.class;
        if (savedInstanceState == null) {
            Log.e(TAG, "savedInstanceState:==null " );
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment(getString(R.string.app_name));
        }
    }

    private void loadHomeFragment(String string) {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
      //  drawer.closeDrawers();
        invalidateOptionsMenu();

    }

    private Fragment getHomeFragment() {
        Log.e(TAG, "getHomeFragment: " );
        switch (navItemIndex) {
            case 0:
                // home
                RecentsFragment recentsFragment = new RecentsFragment();
                return recentsFragment;

            case 1:
                NearbyFragment nearbyFragment = new NearbyFragment();
                return nearbyFragment;

            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            case 3:
                FoodFragment foodFragment = new FoodFragment();
                return foodFragment;

            case 4:
                Personal personal = new Personal();
                /*return personal;*/

            default:
                return new RecentsFragment();
        }
    }



    private void  getView() {
        context=Dashboard.this;
        mHandler = new Handler();
        navigationView();
        bottomBar = (BottomNavigationView) findViewById(R.id.bottomBar);
        //OnNavigationItemSelectedListener
        bottomBar.setOnNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void navigationView() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView user_name = (TextView)header.findViewById(R.id.nav_username);
        TextView firstName = (TextView)header.findViewById(R.id.nav_irid);
        user_name.setText(strname);
        firstName.setText(stririd);
    }


    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.txt_eacash:
                BankDetails.StartActivity(context);
                break;
            case R.id.txt_buymore:
                Products.StartActivity(context);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            finish();
        }
    }

    public static void StartActivity(Context context,Bundle bundle,int value) {
        context.startActivity(new Intent(context,Dashboard.class));
        if (value==0) {
            if (bundle != null) {
                strIRID = bundle.getString("IRID");
            }
        }else {
            if (bundle != null) {
                stririd = bundle.getString("IR_ID");
                strmobile = bundle.getString("IR_MobileNo");
                strname = bundle.getString("IR_Name");
                strmsg = bundle.getString("Message");
            }
        }
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
                File photoFile = createImageFile();
                imagePath = photoFile.getAbsolutePath();
                //   intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

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
            }catch (Exception e) {
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

    private Uri getCamaraImageUri(Intent data) {
        Uri uri = null;
        try {
            Uri selectedImage = data.getData();
            Log.e(TAG, "uri: "+selectedImage );
            Intent intent = getIntent();
            String image_path= intent.getStringExtra("test");
            Uri fileUri = Uri.parse(image_path);
             // displayImage(imageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
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

    private void displayImage(Uri photoUri) {
        linAdd.setVisibility(View.GONE);
        Glide.with(this).load(photoUri).into(circleImageView);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission() {
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this) &&
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, this) && checkPermission(Manifest.permission.CAMERA, this)) {
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.REQUEST_PERMISSION_WRITE_STORAGE);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.e(TAG, "onNavigationItemSelected: "+id );

        if (id == R.id.tab_recents) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment(getString(R.string.app_name));

        } else if (id == R.id.tab_favorites) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_FAV;
            loadHomeFragment(getString(R.string.app_name));

        } else if (id == R.id.tab_nearby) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_NEAR;
            loadHomeFragment(getString(R.string.app_name));

        } else if (id == R.id.tab_friends) {
            navItemIndex = 3;
            CURRENT_TAG = TAG_FRIENDS;
            loadHomeFragment(getString(R.string.app_name));

        } else if (id == R.id.tab_food) {
            navItemIndex = 4;
            CURRENT_TAG = TAG_FOOD;
            loadHomeFragment(getString(R.string.app_name));
        } else if (id == R.id.upload_details) {
            UploadDetaills.StartActivity(context);
        }else if (id == R.id.personal_Details_nav)
        {
            PersonalDetailsActivity.StartActivity(context);

        }else if (id == R.id.nominee_details)
        {
            NomineeDetails.StartActivity(context);
        }else if (id == R.id.Bank_Details)
        {
            BankDetails.StartActivity(context);
        }else if (id == R.id.Change_pin) {
            //NomineeDetails.StartActivity(context);
        }
        else{
            loadHomeFragment(getString(R.string.app_name));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri)   {
        Log.e(TAG, "onFragmentInteraction: " );
    }
}
