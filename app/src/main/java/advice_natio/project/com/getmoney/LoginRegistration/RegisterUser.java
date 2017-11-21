package advice_natio.project.com.getmoney.LoginRegistration;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.regex.Pattern;

import advice_natio.project.com.getmoney.Activites.NomineeDetails;
import advice_natio.project.com.getmoney.Activites.Products;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.Constants;
import advice_natio.project.com.getmoney.BaseActivity.Verhoeff;
import advice_natio.project.com.getmoney.Fragment.DatePickerFragment;
import advice_natio.project.com.getmoney.Modal.ProductList;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 8/23/2017.
 */

public class RegisterUser extends BaseActivity implements View.OnClickListener
{
    private Context context;
    private Toolbar toolbar;
    private EditText edtIrId,edtfristname,edtmiddlename,edtlastname,edtemails,edtmoblie,edtpannumber,edtaadharnumber,
            edtaddress1,edtaddress2,edtcity,edtstate,edtpinnumber,edtfathername,edtmothername,edtmaritalsat;
    private TextInputLayout txtinIrID,txxifristname,txtinnmiddlename,txtinlastname,txtinemails,txtinmobile,txtinpannumber,txtaadharnuer,
    txtinaddress1,txtinaddress2,txtincity,txtstate,txtpinnumber,txtinfathername,txtmothername,txtmaritalstate;
    private RadioButton rdtMale,rdtFemale,rdtOther;
    private DatePicker datepicker;
    private TextView txtdate;
    private AppCompatButton btnRegister;
    private String TAG="Registration Activity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        context = this;
        Myperrmission();
        InitiStatus();
        InitiToolbar((Toolbar)findViewById(R.id.toolbar),getResources().getString(R.string.titile_registration));
        IniView();
    }

    private void IniView() {
        btnRegister = (AppCompatButton) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        edtaadharnumber  =(EditText) findViewById(R.id.edtaadharnumber);
        edtpannumber  =(EditText) findViewById(R.id.edtpannumber);
        txtaadharnuer = (TextInputLayout) findViewById(R.id.txtaadharnuer);
        txtinpannumber =(TextInputLayout) findViewById(R.id.txtinpannumber);
        edtaadharnumber.addTextChangedListener(new MyTextWatcher(edtaadharnumber));
        edtpannumber.addTextChangedListener(new MyTextWatcher(edtpannumber));
        txtdate = (TextView) findViewById(R.id.txtdate);
        txtdate.setOnClickListener(this);
    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,RegisterUser.class));
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case  R.id.btnRegister:
                submit();
                break;
            case R.id.txtdate:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");

                break;
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

    private void submit()
    {
        if (!validatePan())
        {
             return;
        }

        if (!validateAadhar())
        {
            return;
        }

        NomineeDetails.StartActivity(context);
    }



    private boolean validateAadhar() {
        String aadhar = edtaadharnumber.getText().toString().trim();
        if (!Verhoeff.validateAadhar(aadhar) || aadhar.isEmpty()) {
            txtaadharnuer.setError(getResources().getString(R.string.error_aadhar));
            requestFocus(edtaadharnumber);
            return false;
        }else {
            txtaadharnuer.setErrorEnabled(false);
        }
       return true;
    }

    private boolean validatePan() {
        String pan = edtpannumber.getText().toString().trim();
        pan=pan.toUpperCase();
        if (!Verhoeff.panvalidaton(pan)) {
            txtinpannumber.setError(getResources().getString(R.string.error_pan));
            requestFocus(edtpannumber);
            return false;
        }else {
            txtinpannumber.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
       if (view.requestFocus()) {
          getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
       }
     }

    /*private static boolean isValidEmail(String aadhar) {
        return !TextUtils.isEmpty(aadhar) && android.util.Patterns.EMAIL_ADDRESS.matcher(aadhar).matches();
    }*/

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edtaadharnumber:
                    validateAadhar();
                    break;
                case R.id.edtpannumber:
                    validatePan();
                    break;
            }
        }
    }


  /*  private String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();


        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();

        String actype = null;
        String acname = null;
        String phoneNumber = null;
        for (Account ac : accounts) {
            acname = ac.name;
            actype = ac.type;
            // Take your time to look at all available accounts
            System.out.println("Accounts : " + acname + ", " + actype);
            if (actype.equals("com.whatsapp")) {
                phoneNumber = ac.name;
                //phoneNumber = ac.type;
            }
        }

        Log.d(TAG, "Phone number" + phoneNumber);

        return phoneNumber;
    }*/


    public void Myperrmission()
    {

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA
                    , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, Constants.REQUEST);
            } else {
                //Do here
            }
        } else {
            //Do here
        }
    }


}
