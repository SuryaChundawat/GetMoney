package advice_natio.project.com.getmoney.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import advice_natio.project.com.getmoney.BaseActivity.AnimationUtils;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.PostApi;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 11/18/2017.
 */

public class Personal extends Fragment  {

    private TextView txtIrId;
    private EditText edt_irname,edt_dateofbirth,edt_mobile,edt_emails,edt_address1,edt_address2,edt_pincode,edt_fathername,
            edt_mothername;
    private Spinner spn_gender,spn_city,spn_state,spn_country,spn_maritalstatus;
    private Button btnSubmit;
    private String strname,strdateofbirth,strmobile,stremails,straddress,straddress2,strpincode,strfathername,strmother;
    private OnFragmentInteractionListener mListener;
    private Context context;
    private ProgressBar progressBar;
    private String TAG ="PersonalDetailsActivity";
    private PostApi postApi;

    public Personal() {
        //Surya Narayan Singh Chundawat
        //Class
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview  = inflater.inflate(R.layout.activity_personaldetails, container, false);
        //GetRef(rootview);
        //Inflate the layout for this fragment.
        return rootview;
    }

   /* private void GetRef(View rootview) {
        edt_irname = (EditText) rootview.findViewById(R.id.edt_irname);
        edt_dateofbirth = (EditText) rootview.findViewById(R.id.edt_dateofbirth);
        edt_mobile =(EditText) rootview.findViewById(R.id.edt_mobile);
        edt_emails = (EditText) rootview.findViewById(R.id.edt_emails);
        edt_address1 = (EditText) rootview.findViewById(R.id.edt_address1);
        edt_address2 =(EditText) rootview.findViewById(R.id.edt_address2);
        edt_pincode =  (EditText) rootview.findViewById(R.id.edt_pincode);
        edt_fathername = (EditText) rootview.findViewById(R.id.edt_fathername);
        edt_mothername = (EditText) rootview.findViewById(R.id.edt_mothername);
        spn_gender = (Spinner) rootview.findViewById(R.id.spn_gender);
        spn_city = (Spinner) rootview.findViewById(R.id.spn_city);
        spn_state = (Spinner) rootview.findViewById(R.id.spn_state);
        spn_country =(Spinner) rootview.findViewById(R.id.spn_country);
        spn_maritalstatus =(Spinner) rootview.findViewById(R.id.spn_maritalstatus);
        btnSubmit  = (Button) rootview.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }*/

   /* private void submit()
    {
        strname = edt_irname.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strdateofbirth = edt_dateofbirth.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strmobile = edt_mobile.getText().toString().replaceAll("\\s{2,}", " ").trim();
        stremails = edt_address1.getText().toString().replaceAll("\\s{2,}", " ").trim();
        straddress = edt_address1.getText().toString().replaceAll("\\s{2,}", " ").trim();
        straddress2 = edt_address2.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strpincode = edt_pincode.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strfathername = edt_fathername.getText().toString().replaceAll("\\s{2,}", " ").trim();
        strmother = edt_mothername.getText().toString().replaceAll("\\s{2,}", " ").trim();

        if (strname == null || strname.equals("null")|| strname.isEmpty()) {
            showSnackbar(edt_irname,"Enter Name");
        }else if (strdateofbirth == null || strdateofbirth.equals("null")|| strdateofbirth.isEmpty()) {
            showSnackbar(edt_irname,"Enter DateOfBirth");
        }else if (strmobile == null || strmobile.equals("null")|| strmobile.isEmpty()) {
            showSnackbar(edt_irname,"Enter Mobile");
        }else if (stremails == null || stremails.equals("null")|| stremails.isEmpty()) {
            showSnackbar(edt_irname,"Enter Mobile");
        }else if (straddress == null || straddress.equals("null")|| straddress.isEmpty()) {
            showSnackbar(edt_irname,"Enter Address1");
        }else if(straddress2 == null || straddress2.equals("null")|| straddress2.isEmpty()) {
            showSnackbar(edt_irname,"Enter Address2");
        }else if (strpincode == null || strpincode.equals("null")|| strpincode.isEmpty()) {
            showSnackbar(edt_irname,"Enter pincode");
        }else if (strfathername == null || strfathername.equals("null")|| strfathername.isEmpty()) {
            showSnackbar(edt_irname,"Enter FatherName");
        }else if (strmother == null || strmother.equals("null")|| strmother.isEmpty()) {
            showSnackbar(edt_irname,"Enter MotherName");
        }else {
            callback(3);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        mListener = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.btnSubmit:
                if (isOnline(context)) {
                    submit();
                }
                break;
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

        //{"jsonObject_Details":[{"IR_ID" : "17330001","IR_EmailID" : "test@b.com","IR_Gender" : "M","IR_Address1" : "abc",
        // "IR_Address2" : "xyz","IR_Country" : "IND","IR_State" : "1","IR_City" : "1","IR_PinCode" : "100001",
        // "IR_FatherName" : "FAT","IR_MotherName" : "MOT","IR_MaritalStatus" : "S"}]}


        try {
            jsonObject1.put("IR_ID", "12345678960");
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
        if (id ==0) {
            showProgress(false);
            try {
                String IRID = response.getString("IR_ID");
                String  Details = response.getString("Message");
                String status =response.getString("Status");
                if(status.equals("Success")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IRID",IRID);
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

    protected boolean isOnline(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    protected void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }*/



}
