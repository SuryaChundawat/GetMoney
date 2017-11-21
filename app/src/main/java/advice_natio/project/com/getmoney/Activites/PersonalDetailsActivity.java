package advice_natio.project.com.getmoney.Activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.Fragment.OnFragmentInteractionListener;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.PostApi;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 10/29/2017.
 */

public class PersonalDetailsActivity extends BaseActivity implements View.OnClickListener,ApiResponse {

    private TextView txtIrId;
    private EditText edt_emails,edt_address1,edt_address2,edt_fathername,
            edt_mothername,edt_pincode;
    private Spinner spn_gender,spn_city,spn_state,spn_country,spn_maritalstatus;
    private Button btnSubmit;
    private String strname,strdateofbirth,strmobile,stremails,straddress,straddress2,strpincode,strfathername,strmother;
    private OnFragmentInteractionListener mListener;
    private Context context;
    private ProgressBar progressBar;
    private String TAG ="PersonalDetailsActivity";
    private PostApi postApi;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetails);
        GetRef();
        context = this;
        InitiToolbar((Toolbar)findViewById(R.id.toolbar),"Personal Details");
    }

    //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_EmailID" : "test@b.com","IR_Gender" : "M","IR_Address1" : "abc",
    // "IR_Address2" : "xyz","IR_Country" : "IND","IR_State" : "1","IR_City" : "1","IR_PinCode" : "100001",
    // "IR_FatherName" : "FAT","IR_MotherName" : "MOT","IR_MaritalStatus" : "S"}]}

    private void GetRef( ) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        txtIrId = (TextView) findViewById(R.id.txtIrId);
        edt_emails = (EditText) findViewById(R.id.edt_emails);
        spn_gender = (Spinner) findViewById(R.id.spn_gender);
        edt_address1 = (EditText) findViewById(R.id.edt_address1);
        edt_address2 = (EditText) findViewById(R.id.edt_address2);
        edt_pincode =(EditText) findViewById(R.id.edt_pincode);
        spn_city = (Spinner) findViewById(R.id.spn_city);
        spn_state = (Spinner) findViewById(R.id.spn_state);
        spn_country = (Spinner) findViewById(R.id.spn_country);
        edt_fathername = (EditText) findViewById(R.id.edt_fathername);
        edt_mothername = (EditText) findViewById(R.id.edt_mothername);
        spn_maritalstatus = (Spinner) findViewById(R.id.spn_maritalstatus);


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        String irid=sharedPreferences.getString("irid","");
        Log.d(TAG,"irid"+irid);
        txtIrId.setText(irid);
    }


    private void submit() {

            stremails = edt_address1.getText().toString().replaceAll("\\s{2,}", " ").trim();
            straddress = edt_address1.getText().toString().replaceAll("\\s{2,}", " ").trim();
            straddress2 = edt_address2.getText().toString().replaceAll("\\s{2,}", " ").trim();
            strfathername = edt_fathername.getText().toString().replaceAll("\\s{2,}", " ").trim();
            strmother = edt_mothername.getText().toString().replaceAll("\\s{2,}", " ").trim();
            strpincode = edt_mothername.getText().toString().replaceAll("\\s{2,}", " ").trim();

            if (stremails == null || stremails.equals("null")|| stremails.isEmpty()) {
                showSnackbar(edt_address1,"Enter Mobile");
            }else if (straddress == null || straddress.equals("null")|| straddress.isEmpty()) {
                showSnackbar(edt_address1,"Enter Address1");
            }else if(straddress2 == null || straddress2.equals("null")|| straddress2.isEmpty()) {
                showSnackbar(edt_address1,"Enter Address2");
            }else if (strpincode == null || strpincode.equals("null")|| strpincode.isEmpty()) {
                showSnackbar(edt_address1,"Enter pincode");
            }else if (strfathername == null || strfathername.equals("null")|| strfathername.isEmpty()) {
                showSnackbar(edt_address1,"Enter FatherName");
            }else if (strmother == null || strmother.equals("null")|| strmother.isEmpty()) {
                showSnackbar(edt_address1,"Enter MotherName");
            }else {
                callback(3);
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





    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.btnSubmit:
                if (isOnline(context)) {
                    submit();
                } else {
                    showSnackbar(edt_address1,"Oops !! Please Check Internet.");
                }
                break;
        }
    }


    private JSONObject GetLoginObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_EmailID" : "test@b.com","IR_Gender" : "M","IR_Address1" : "abc",
        // "IR_Address2" : "xyz","IR_Country" : "IND","IR_State" : "1","IR_City" : "1","IR_PinCode" : "100001",
        // "IR_FatherName" : "FAT","IR_MotherName" : "MOT","IR_MaritalStatus" : "S"}]}
        try {
            jsonObject1.put("IR_ID", txtIrId.getText().toString().trim());
            jsonObject1.put("IR_EmailID",stremails);
            jsonObject1.put("IR_Gender","M");
            jsonObject1.put("IR_Address1",straddress);
            jsonObject1.put("IR_Address2",straddress2);
            jsonObject1.put("IR_Country","IND");
            jsonObject1.put("IR_State","18");
            jsonObject1.put("IR_City","100");
            jsonObject1.put("IR_PinCode",strpincode);
            jsonObject1.put("IR_FatherName",strfathername);
            jsonObject1.put("IR_MotherName",strmother);
            jsonObject1.put("IR_MaritalStatus","S");
            jsonArray.put(jsonObject1);
            jsonObject.put("jsonObject_Details",jsonArray);

        }catch (JSONException e) {
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
            //showProgress(false);
            try {
                String IRID = response.getString("IR_ID");
                String  Details = response.getString("Message");
                String status =response.getString("Status");
                if(status.equals("Success")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IRID",IRID);
                    showSnackbar(btnSubmit,status);
                } else {
                    showSnackbar(btnSubmit,status);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void OnFailed(int error, int id) {

    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,PersonalDetailsActivity.class));

    }
}
