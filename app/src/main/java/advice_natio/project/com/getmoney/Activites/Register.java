package advice_natio.project.com.getmoney.Activites;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.PostApi;
import advice_natio.project.com.getmoney.R;
import advice_natio.project.com.getmoney.scrap.DashBoard;

/**
 * Created by Surya Chundawat on 11/11/2017.
 */

public class Register extends BaseActivity implements View.OnClickListener,ApiResponse {
    private AppCompatButton btnSubmit;
    private ProgressBar progressBar;
    private EditText edtname,edtmiddlename,edtlastname,edtifsccode,edtemails;
    private CheckBox termscheck;
    private TextView edtdate;
    private Context context;
    private String name,middlename,lastname,date,email;
    private String TAG="Register.class";
    private PostApi postApi;
    private Calendar calendar;
    private int year, month, day;
    private long timestamp;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        context = this;
        Initilizationview();
        Inidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Initilizationview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.GONE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        //setSupportActionBar(toolbar);
        /*getSupportActionBar().setTitle("Register");*/
        //toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        /*toolbar.setTitleTextColor(Color.parseColor("#ffffff"));*/

        btnSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
        progressBar =(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        btnSubmit.setOnClickListener(this);
        edtdate = (TextView) findViewById(R.id.edtifsccode);
        edtdate.setOnClickListener(this);
        edtname = (EditText) findViewById(R.id.edtname);
        edtmiddlename = (EditText) findViewById(R.id.edtmiddlename);
        edtlastname = (EditText) findViewById(R.id.edtlastname);
        //edtifsccode = (EditText) findViewById(R.id.edtifsccode);
        edtemails =(EditText) findViewById(R.id.edtemails);
        termscheck = (CheckBox) findViewById(R.id.termsandcondition);


    }

    @Override
    public void onClick(View view) {
        if (view == btnSubmit) {
            if (isOnline(context)) {
                showProgress(true);
                submit();
            }else {
                showSnackbar(btnSubmit,"Ooops!! Check your Network");
            }
        }
        if(view.getId()==R.id.edtifsccode){
            showSnackbar(edtdate,"date is clicked");
            showDialog(999);
        }

    }


    private void submit() {
        name = edtname.getText().toString().replaceAll("\\s{2,}", " ").trim();
        middlename = edtmiddlename.getText().toString().replaceAll("\\s{2,}", " ").trim();
        lastname = edtlastname.getText().toString().replaceAll("\\s{2,}", " ").trim();
        date = edtdate.getText().toString().replaceAll("\\s{2,}", " ").trim();
        email = edtemails.getText().toString().trim();
        Log.d(TAG,"name: " + name +"middlename"+middlename+"lastname"+lastname+"date"+date+"email"+email);
        if (name == null || name.isEmpty() || name.equals("null")) {
            showSnackbar(edtname,"Please Enter Name");
        }else if (middlename == null || middlename.isEmpty() || middlename.equals("null")) {
            showSnackbar(edtmiddlename,"Please Middle Name");
        }else if (lastname == null || lastname.isEmpty() || lastname.equals("null") ) {
            showSnackbar(edtlastname,"Enter Last Name");
        }else if (date == null || date.isEmpty() || date.equals("null")) {
            showSnackbar(edtdate,"Enter Date");
        }else if (email == null || email.isEmpty() || email.equals("null")) {
            showSnackbar(edtemails,"Enter Email Address");
        }else {
            if (isOnline(context)) {
                showProgress(true);
                callback(0);

            } else {
                showSnackbar(edtemails,"Oops !! Check Your Network.");
            }
        }
    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,Register.class));
    }

    private void callback(int id, String...data) {
        switch (id) {
            case 0:
                String URL = NetworkUrl.REGISTRATION;
                String apiTag = NetworkUrl.REGISTRATION;
                JSONObject jsonObject = GetLoginObject();
                Log.e(TAG, "callback: json" + jsonObject.toString());
                postApi = new PostApi(context, URL, jsonObject, apiTag, TAG ,0);  // 1 is id for call deshboard api
                break;

            default:
                break;
        }
    }

    private JSONObject GetLoginObject() {
        JSONObject jobject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("IR_ReferralID","CASHJET" );
            jsonObject.put("IR_MobileNo", "9702603779");
            jsonObject.put("IR_FirstName",name);
            jsonObject.put("IR_MiddleName",middlename);
            jsonObject.put("IR_LastName",lastname);
            jsonObject.put("IR_EmailID",email);
            jsonObject.put("IR_DOB",date);
            jsonObject.put("IsNew","Y");
            jsonArray.put(jsonObject);
            jobject.put("jsonObject_Details",jsonArray);
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "GetLoginObject: " + e.getMessage());
        }
        Log.d(TAG,"Registration Object"+jobject);
        return jobject;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Inidate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        try {
            String dateofbirth=day+"-"+month+"-"+year;
            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date date= (Date)formatter.parse(dateofbirth);
            long output=date.getTime()/1000L;
            String str=Long.toString(output);
            timestamp = Long.parseLong(str) * 1000;
            Log.i(TAG, "showDate: "+timestamp );
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "GetDateexception " + e.getMessage());
        }

        edtdate.setText(new StringBuilder().append(day).append("-")
                .append("OCT").append("-").append(year));
        Log.d(TAG,"Day time stap "+edtdate.getText().toString());
    }




    @Override
    public void OnSucess(JSONObject response, int id) {
        Log.e(TAG, "OnSucess: "+id+"  "+ response);
        if (id ==0) {
            showProgress(false);
            try {
                String IRID = response.getString("IR_ID");
                String  Details = response.getString("Message");
                String status =response.getString("Status");
                if(status.equals("Success")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IRID",IRID);
                    editor.putString("irid",IRID);
                    editor.commit();
                    Dashboard.StartActivity(context,bundle,0);
                } else {
                    showSnackbar(btnSubmit,status);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void showProgress(boolean b) {
        if (b) {
            AnimationUtils.animateScaleOut(progressBar);
        } else {
            AnimationUtils.animateScaleIn(progressBar);
        }
    }


    @Override
    public void OnFailed(int error, int id) {
        Log.e(TAG, "OnFailed:" + error);
        showProgress(false);
        showSnackbar(btnSubmit, "Error Code = "+String.valueOf(id));
    }
}
