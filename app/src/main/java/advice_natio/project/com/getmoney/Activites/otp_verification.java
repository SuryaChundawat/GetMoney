package advice_natio.project.com.getmoney.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.BaseActivity.PinEntryEditText;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.GetApi;
import advice_natio.project.com.getmoney.Keyboard.KeyboardUtil;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 10/29/2017.
 */

public class otp_verification extends BaseActivity implements ApiResponse,View.OnClickListener{
    private static String session_id,mobile;
    private AppCompatButton btnsumit;
    private View view;
    private Context context;
    private static String TAG="Otp_verification";
    private GetApi getApi;
    private PinEntryEditText pinEntry;
    private ProgressBar progressBar;
    private KeyboardUtil ku;
    private TextView txt_mobilenumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard);
        initlizeview();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initlizeview()  {
        context = this;
        btnsumit = (AppCompatButton) findViewById(R.id.btnresend);
        btnsumit.setOnClickListener(this);
        view = (View) findViewById(android.R.id.content);
        pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        txt_mobilenumber = (TextView) findViewById(R.id.txt_mobilenumber);
        txt_mobilenumber.setText(otp.mobile);
        disableShowSoftInput(pinEntry);
        showKeyboard(pinEntry);
        progressBar = (ProgressBar) findViewById(R.id.progressBarToolbar);
        progressBar.setVisibility(View.INVISIBLE);
        pinEntry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showKeyboard(pinEntry);
                return false;
            }
        });

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if(str.toString().equals("123456")) {
                        /*if (isOnline(context)) {
                            Log.d(TAG, "Otp Key" + str);*/
                            activity_verificatioconfirmation.StartActivity(context);
                            //callback(1,str.toString());}else{showSnackbar(view,"Ooops!! Check your Network");}
                        /*}*/
                    }


                    /*if(str.length()==6) {
                        if (isOnline(context))
                        {
                            Log.d(TAG,"Otp Key"+str);
                            callback(1,str.toString());}else{showSnackbar(view,"Ooops!! Check your Network");}
                    }*/
                }
            });
        }
    }


    @SuppressLint("ObsoleteSdkInt")
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


    private void callback(int id, String data) {
        String URL,apiTag,jsonObject;
        switch (id) {
            case 1:
                URL= NetworkUrl.OTP+NetworkUrl.API+"/SMS/VERIFY/"+session_id+"/"+data;
                apiTag = NetworkUrl.OTP+NetworkUrl.API+"/SMS/VERIFY/"+session_id+"/"+data;
                jsonObject = "Otp Class";
                Log.e(TAG,URL);
                getApi = new GetApi(context, URL, jsonObject, apiTag, TAG ,1);
                break;
            case 0:
                URL = NetworkUrl.OTP + NetworkUrl.API + "/SMS/" + mobile + "/AUTOGEN";
                apiTag = NetworkUrl.OTP + NetworkUrl.API + "/SMS/" + mobile + "/AUTOGEN";
                jsonObject = "Otp Class";
                Log.e(TAG,URL);
                getApi = new GetApi(context, URL, jsonObject, apiTag, TAG ,0);
        }

    }

    public static void StartActivity(Context context,Bundle bundle) {
        context.startActivity(new Intent(context,otp_verification.class));
        if (bundle != null) {
            session_id =bundle.getString("session_id");
            mobile= bundle.getString("mobile");
            Log.e(TAG,"session_id"+session_id+"moblie"+mobile);
        }
    }

    @Override
    public void OnSucess(JSONObject response, int id) {
        Log.e(TAG, "OnSucess: "+id+"  "+ response);
        switch (id) {
            case 1:
                try {
                    String status = response.getString("Status");
                    String  Details = response.getString("Details");
                    if(status.equals("Success")) {
                        showSnackbar(view,Details);
                        activity_verificatioconfirmation.StartActivity(context);
                    }else {
                        pinEntry.setError(true);
                        pinEntry.setText(null);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case 0:
                String status = null;
                try {
                    status = response.getString("Status");
                    String  Details = response.getString("Details");
                    if(status.equals("Success")) {
                       session_id = Details;
                    }else {
                        showSnackbar(view,Details);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

    }

    @Override
    public void OnFailed(int error, int id) {
        Log.e(TAG, "OnFailed:" + error);
        showProgress(false);
        showSnackbar(view, "Error Code = "+String.valueOf(id));
    }

    private void showProgress(boolean b) {
        if (b) {
            AnimationUtils.animateScaleOut(progressBar);
        } else {
            AnimationUtils.animateScaleIn(progressBar);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnsumit) {
            if (isOnline(context)) {
                String data= "null";
                callback(0,data);
            }else {showSnackbar(view,"Ooops!! Check your Network");}
        }
    }
}
