package advice_natio.project.com.getmoney.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import advice_natio.project.com.getmoney.BaseActivity.NetworkUrl;
import advice_natio.project.com.getmoney.Https.ApiResponse;
import advice_natio.project.com.getmoney.Https.PostApi;
import advice_natio.project.com.getmoney.R;

/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentsFragment#newInstance} factory method to
 * create an instance of this fragment.
*/
public class RecentsFragment extends Fragment implements ApiResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView txtIrId,txtdashName,txtdashdate,txtdash_available_points;

    private OnFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String TAG="PersonalFragment";
    private PostApi postApi;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_recents, container, false);
       // GetRef(rootview);
        // Inflate the layout for this fragment
        return rootview;
    }

    @SuppressLint("CommitPrefEdits")
    private void GetRef(View view)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        txtIrId = (TextView) view.findViewById(R.id.txtdashIrId);
        txtdashName =(TextView) view.findViewById(R.id.txtdashName);
        txtdashdate = (TextView) view.findViewById(R.id.txtdashdate);
        txtdash_available_points= (TextView) view.findViewById(R.id.txtdash_available_points);
        String irid = sharedPreferences.getString("irid","");
        txtIrId.setText(irid);
        submit();
    }

    private void submit() {
        if (isOnline(context)) {
            //showProgress(true);
           // callback(0);
        } else {
            showSnackbar(txtIrId,"Ooops!! Check your Network");
        }
    }

    private void callback(int id, String...data) {
        switch (id) {
            case 0:
                String URL = NetworkUrl.URL_ENCASHPOINTS;
                String apiTag = NetworkUrl.URL_ENCASHPOINTS;
                JSONObject jsonObject = GetLoginObject();
                Log.e(TAG, "callback: json" + jsonObject.toString());
                postApi = new PostApi(context, URL, jsonObject, apiTag, TAG ,0);  // 1 is id for call deshboard api
                break;

            default:
                break;
        }
    }

    private JSONObject GetLoginObject() {
        String irid=sharedPreferences.getString("irid","");
        Log.d(TAG,"irid"+irid);
        JSONObject jobject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("IR_ID",irid );
            jsonArray.put(jsonObject);
            jobject.put("jsonObject_Details",jsonArray);
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "GetLoginObject: " + e.getMessage());
        }
        Log.d(TAG,"Registration Object"+jobject);
        return jobject;
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
        mListener = (OnFragmentInteractionListener) context;
        this.context= context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    protected boolean isOnline(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }


    //{"IR_EncashDetailsList":[{"IR_CreditAmount":"600.00","IR_Date":"10\/08\/2017 10:09:44 PM",
    // "IR_EncashPoints":"1000.00","IR_ID":"17330001","IR_WeekNo":"41","IR_YearNo":"2017",
    // "Message":"","Status":"Success"}]}

    @Override
    public void OnSucess(JSONObject response, int id) {
        Log.e(TAG, "OnSucess: "+id+"  "+ response);
        if (id ==3) {
            //showProgress(false);
            try {
                String status =response.getString("IR_EncashDetailsList");
               /* JSONArray jsonArray = */
                if(status.equals("Success")) {
                    showSnackbar(txtdashName,status);
                    String IR_DOB =response.getString("IR_DOB");
                    String IR_EmailID =response.getString("IR_EmailID");
                    String IR_FirstName =response.getString("IR_FirstName");
                    String IR_ID =response.getString("IR_ID");
                    String IR_LastName =response.getString("IR_LastName");
                    String IR_MiddleName =response.getString("IR_MiddleName");
                    String IR_MobileNo =response.getString("IR_MobileNo");
                    String Message =response.getString("Message");




                } else {
                    showSnackbar(txtdashName,status);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void OnFailed(int error, int id) {

    }
}
