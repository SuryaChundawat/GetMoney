package advice_natio.project.com.getmoney.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.GetApi;
import advice_natio.project.com.getmoney.Https.PostApi;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 10/29/2017.
 */

public class otp extends BaseActivity implements View.OnClickListener,ApiResponse {

    private FloatingActionButton fb;
    private EditText edt_mobilenuber;
    private Context context;
    private GetApi getApi;
    public static String mobile;
    private View viewpart;
    private ProgressBar progressBarToolbar;
    private String TAG = "otp";
    private PostApi postApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initilizeview();
        context = this;
    }

    private void initilizeview() {
        fb = (FloatingActionButton) findViewById(R.id.fb);
        fb.setOnClickListener(this);
        edt_mobilenuber = (EditText) findViewById(R.id.edtmobileotp);
        viewpart = findViewById(android.R.id.content);
        progressBarToolbar = (ProgressBar) findViewById(R.id.progressBarToolbar);
        progressBarToolbar.setVisibility(View.INVISIBLE);
    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,otp.class));
    }

    @Override
    public void onClick(View view) {

        if (view == fb ) {
            mobile = edt_mobilenuber.getText().toString().replaceAll("\\s{2,}", " ").trim();
            if (mobile == null || mobile.isEmpty() || mobile.equals("null")) {
                showSnackbar(edt_mobilenuber, "Please Enter Username");
            } else if (mobile.length()<10)
            {
                showSnackbar(edt_mobilenuber,"Please Enter Valid Mobile Number");
            }else {
                if (isOnline(context)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("session_id","");
                    bundle.putString("mobile",mobile.trim());
                    otp_verification.StartActivity(context,bundle);
                    //1showProgress(true);
                    //callback(1);
                } else {showSnackbar(view,"Ooops!! Check your Network");}
            }
        }
    }


    private void callback(int id, String...data) {
        switch (id) {
            case 0:
                String URL= NetworkUrl.OTP+NetworkUrl.API+"/SMS/"+mobile+"/AUTOGEN";
                String apiTag = NetworkUrl.OTP+NetworkUrl.API+"/SMS/"+mobile+"/AUTOGEN";
                String  jsonObject = "Otp Class";
                Log.e(TAG,URL);
                getApi = new GetApi(context, URL, jsonObject, apiTag, TAG ,0);
                break;
            case 1:
                String URLCHECKIRID = NetworkUrl.URL_CHECKIR_ID;
                String apiTag_URLCHECKIRID = NetworkUrl.URL_CHECKIR_ID;
                JSONObject jsonObjectnew = GetLoginObject();
                postApi = new PostApi(context , URLCHECKIRID , jsonObjectnew , apiTag_URLCHECKIRID,TAG,1);
                break;
        }
    }


    public JSONObject GetLoginObject() {
        JSONObject jobject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("IR_MobileNo", mobile);
            jsonArray.put(jsonObject);
            jobject.put("jsonObject_Details",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "GetLoginObject: " + e.getMessage());
        }
        return jobject;
    }

    private void showProgress(boolean b) {
        if (b) {
            AnimationUtils.animateScaleOut(progressBarToolbar);
        } else {
            AnimationUtils.animateScaleIn(progressBarToolbar);
        }
    }

    @Override
    public void OnSucess(JSONObject response, int id) {
        Log.e(TAG, "OnSucess: "+id+"  "+ response);
        if (id ==0) {
            showProgress(false);
            try {
                String status = response.getString("Status");
                String  Details = response.getString("Details");
                if(status.equals("Success")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("session_id",Details);
                    bundle.putString("mobile",mobile.trim());
                    otp_verification.StartActivity(context,bundle);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (id ==1) {
            showProgress(false);
            try {
                String status = response.getString("Status");
                String IR_ID= response.getString("IR_ID");
                String IR_MobileNo =response.getString("IR_MobileNo");
                String IR_Name = response.getString("IR_Name");
                String Message = response.getString("Message");
                Log.d(TAG,"Status"+status+"IR_ID"+IR_ID+"IR_MoblieNo"+IR_MobileNo+"Ir_Name"+IR_Name+"Message"+Message);
                if (status.equals("Success")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IR_ID",IR_ID);
                    bundle.putString("IR_MobileNo",IR_MobileNo);
                    bundle.putString("IR_Name",IR_Name);
                    bundle.putString("Message",Message);
                    Dashboard.StartActivity(context,bundle,1);
                }else {
                    callback(0);
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnFailed(int error, int id) {
        Log.e(TAG, "OnFailed:" + error);
        showProgress(false);
        showSnackbar(viewpart, "Error Code = "+String.valueOf(id));

    }
}
