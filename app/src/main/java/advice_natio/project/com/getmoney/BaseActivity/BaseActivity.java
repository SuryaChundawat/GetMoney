package advice_natio.project.com.getmoney.BaseActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

import advice_natio.project.com.getmoney.Keyboard.KeyboardUtil;
import advice_natio.project.com.getmoney.R;


/**
 * Created by ubuntu on 15/9/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    protected int FINISH_TIME = 400;
    protected int ANIM_TIME = 300;
    private Dialog dialog;
    private Boolean isSuccess;
    public static   Context mContext;
    private KeyboardUtil ku;

    protected boolean checkPermission(String strPermission, Context context){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int result = ContextCompat.checkSelfPermission(context, strPermission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    public void InitiStatus()
    {
        if(Build.VERSION.SDK_INT>=21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));}
    }

        public void InitiToolbar(Toolbar toolbar,String titile)
        {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
            toolbar.setTitle(titile);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            setSupportActionBar(toolbar);
        }

  /*  public void StartActivity(Context context,Activity activity) {
        context.startActivity(new Intent(context,activity.getClass()));
    }
*/

    protected void finishWithHandler(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, FINISH_TIME);
    }

    protected void statusBarColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
        }
    }


    protected void closeKeyBoard(Activity context) {
        View view =  context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected boolean isOnline(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     *
     * @param fragment Add And Replace
     */
    /*protected void addFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activityContent, fragment, fragment.getTag()).addToBackStack(fragment.getTag()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    protected void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activityContent, fragment, fragment.getTag()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    protected void replaceFragmentWithoutAnim(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activityContent, fragment, fragment.getTag()).commit();
    }*/

    /**
     *
     * @param gravity Activity Material Transition
     */

    protected void setupExplodeWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode fade = new Explode();
            fade.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    protected void setupFadeWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(fade);
        }
    }

    protected void setupSlideWindowAnimations(int startGravity, int endGravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(startGravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(slide);

            slide = new Slide(endGravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    protected void setupExplodeAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode fade = new Explode();
            fade.setDuration(ANIM_TIME);
            getWindow().setEnterTransition(fade);

            Explode slide = new Explode();
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    protected void showToast(String msg,Context context){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.show();
    }

    protected void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    protected void showErrorLog(String error) {
        Log.e(TAG, ""+error);
    }

    protected void moveActivity(Intent intent, Activity context, boolean isFinish){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
            } else {
                startActivity(intent);
            }
            if(isFinish)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void startActivityForResults(Intent intent, Activity context, boolean isFinish, int requestCode){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivityForResult(intent, requestCode,
                        ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
            } else {
                startActivityForResult(intent, requestCode);
            }
            if(isFinish)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showKeyboard(PinEntryEditText pinEntry) {
        if (ku == null) {
            ku = new KeyboardUtil(this, this, pinEntry);
            ku.setShowListener(new KeyboardUtil.onShowCloseListener() {
                @Override
                public void show() {

                }

                @Override
                public void onPush() {

                }

                @Override
                public void close() {

                }
            });
        }
        if (ku.showKeyboard()) {
            ku.startShow();
        }
    }

    public void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }







    protected void stopProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Do here
                } else {
                    Toast.makeText(mContext, "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public static boolean hasPermissions(Context context, String... permissions)
    {
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



   /* void setAlarmForAutoLogout(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, SharedPreferenceUtil.getInt(Constants.KEY_AUTO_LOGOUT_MIN, 30));
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
        alarmMgr.set(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), alarmIntent);
    }*/

}
