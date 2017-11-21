package advice_natio.project.com.getmoney.Activites;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.Fragment.DatePickerFragment;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.PostApi;
import advice_natio.project.com.getmoney.R;


/**
 * Created by Surya Chundawat on 8/13/2017.
 */

public class NomineeDetails extends BaseActivity implements View.OnClickListener,ApiResponse
{
    private TextView txtdate,txtIrId;
    private Context context;
    private EditText edt_nomineename,edt_pan,edt_adhaar;
    private AppCompatButton btnNext;
    private Spinner spn_relation;
    private String strtxtIrid,strnomineename,strpan,straadhar,strdate;
    private String TAG="NomineeDetails";
    private PostApi postApi;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_details);
        setupExplodeWindowAnimations(Gravity.BOTTOM);
        context=this;
        InitiStatus();
        InitiToolbar((Toolbar)findViewById(R.id.toolbar),getResources().getString(R.string.title_nomineeactivity));
        InitiView();
    }

    @SuppressLint("CommitPrefEdits")
    private void InitiView() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        txtIrId = (TextView) findViewById(R.id.txtIrId);
        edt_nomineename = (EditText) findViewById(R.id.edt_nomineename);
        edt_pan = (EditText) findViewById(R.id.edt_pan);
        edt_adhaar = (EditText) findViewById(R.id.edt_adhaar);
        spn_relation = (Spinner) findViewById(R.id.spn_relation);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnNext = (AppCompatButton) findViewById(R.id.btnSubmit);
        btnNext.setOnClickListener(this);
        txtdate = (TextView) findViewById(R.id.txt_date);
        txtdate.setOnClickListener(this);
        String irid=sharedPreferences.getString("irid","");
        Log.d(TAG,"irid"+irid);
        txtIrId.setText(irid);

    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,NomineeDetails.class));
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnnext:
                if (isOnline(context)) {
                    submit();
                } else {
                    showSnackbar(edt_adhaar,"Oops ! Please Check Network.");
                }
                break;
            case R.id.txtdate:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                break;
        }
    }

    private void submit() {
        strtxtIrid = txtIrId.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strnomineename = edt_nomineename.getText().toString().replaceAll("\\s{2,}", " ").trim();
        straadhar = edt_nomineename.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strpan = edt_nomineename.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strdate = txtdate.getText().toString().replaceAll("\\s{2,}", " ").trim();
        if (strtxtIrid == null || strtxtIrid.equals("null")|| strtxtIrid.isEmpty()) {
            showSnackbar(edt_nomineename,"Enter IRID");
        }else if (strnomineename == null || strnomineename.equals("null")|| strnomineename.isEmpty()) {
            showSnackbar(edt_nomineename,"Enter NomineeName");
        }else if (straadhar == null || straadhar.equals("null")|| straadhar.isEmpty()) {
            showSnackbar(edt_nomineename,"Enter aadharNumber");
        }else if (strpan == null || strpan.equals("null")|| strpan.isEmpty()) {
            showSnackbar(edt_nomineename,"Enter PanNumber");
        }else if (strdate == null || strdate.equals("null")|| strdate.isEmpty()) {
            showSnackbar(edt_nomineename,"Enter DateOfBirth");
        }else {
            if (isOnline(context)) {
                callback(3);
            } else {showSnackbar(btnNext,"Oops!! Please Check Network");}
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
        try {
            jsonObject1.put("IR_ID", "17330001");
            jsonObject1.put("IR_NomineeName",strnomineename);
            jsonObject1.put("IR_Relation","M");
            jsonObject1.put("IR_DOB",strdate);
            jsonObject1.put("IR_AadhaarNo",straadhar);
            jsonObject1.put("IR_PANNo",strpan);
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
    public void OnSucess(JSONObject response, int id) {
        Log.e(TAG, "OnSucess: "+id+"  "+ response);
        if (id ==3) {
            showProgress(false);
            try {
                String IRID = response.getString("IR_ID");
                String  Details = response.getString("Message");
                String status =response.getString("Status");
                if(status.equals("Success")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IRID",IRID);
                    showSnackbar(btnNext,status);
                } else {
                    showSnackbar(btnNext,status);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void OnFailed(int error, int id) {

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
}
