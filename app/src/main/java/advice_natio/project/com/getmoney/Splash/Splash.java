package advice_natio.project.com.getmoney.Splash;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import advice_natio.project.com.getmoney.Activites.Dashboard;
import advice_natio.project.com.getmoney.Activites.jsonparsing;
import advice_natio.project.com.getmoney.Activites.otp;
import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.CircleProgressBar;
import advice_natio.project.com.getmoney.LoginRegistration.MainScreen;
import advice_natio.project.com.getmoney.R;


public class Splash extends BaseActivity {

    private CircleProgressBar progressBar;
    //comit from surya


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intialize();
        fullScreen();
        functionality();
    }

    private void functionality() {

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3 * 1000);
                    showProgress(false);
                   // jsonparsing.StartActivity(Splash.this);
                    otp.StartActivity(Splash.this);
              //      MainScreen.startScreen(Splash.this);
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }


    private void intialize() {
        progressBar = (CircleProgressBar) findViewById(R.id.progressBar);
        showProgress(true);
    }

    private void showProgress(boolean b) {
        if (b) {

            AnimationUtils.animateScaleOut(progressBar);
        } else {

            AnimationUtils.animateScaleIn(progressBar);
        }
    }



    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
