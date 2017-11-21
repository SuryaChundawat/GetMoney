package advice_natio.project.com.getmoney.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 9/5/2017.
 */

public class jsonparsing extends BaseActivity implements View.OnClickListener
{
    // json object response url
    private String urlJsonObj = "https://api.androidhive.info/volley/person_object.json";
    // json array response url
    private String urlJsonArry = "https://api.androidhive.info/volley/person_array.json";
    private static String TAG = jsonparsing.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;
    // Progress dialog
    private ProgressDialog pDialog;
    private TextView txtResponse;
    // temporary string to show the parsed response
    private String jsonResponse;
    private JsonObjectRequest jsonObjectRequest;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparsing);
        btnMakeObjectRequest = (Button) findViewById(R.id.btnObjRequest);
        btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);
        txtResponse = (TextView) findViewById(R.id.txtResponse);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        context= this;

    }

    @Override
    public void onClick(View view) {
        if (view == btnMakeObjectRequest) {
            makeJsonObjectRequest();
        }
    }

    private void makeJsonObjectRequest() {
        showpDialog();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObj
                , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GetResponce(response);
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " +    error.getMessage());
                showToast(error.getMessage(),context);
                hidepDialog();

            }
        });
    }

    private void GetResponce(JSONObject responce)
    {
        Log.d(TAG,responce.toString());
        try {
            String name = responce.getString("name");
            String email = responce.getString("email");
            JSONObject jsonObjectphone = responce.getJSONObject("phone");
            String home = responce.getString("home");
            String mobile = responce.getString("mobile");

            jsonResponse += "Name: " + name + "\n\n";
            jsonResponse += "Email: " + email + "\n\n";
            jsonResponse += "Home: " + home + "\n\n";
            jsonResponse += "Mobile: " + mobile + "\n\n";

            txtResponse.setText(jsonResponse);

        } catch (JSONException e) {
            e.printStackTrace();
            showToast(e.getMessage(),context);
        }


    }


    private void showpDialog() {
     if (!pDialog.isShowing())
       pDialog.show();
     }

     private void hidepDialog() {
       if (pDialog.isShowing())
       pDialog.dismiss();
     }

     public  static  void StartActivity(Context context)
     {
         context.startActivity(new Intent(context,jsonparsing.class));
     }
}
