package advice_natio.project.com.getmoney.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.PinEntryEditText;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 10/29/2017.
 */

public class confirmpin extends BaseActivity implements View.OnClickListener {
    private static String strpin;
    private Context context;
    private PinEntryEditText pinEntry;
    private AppCompatImageButton btnnext;
    private String TAG="confiempin.class";
    private TextView txt_pintitile;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmsetuppin);
        context= this;
        intilizeview();
    }

    /*private void Initilizeview()
    {
       *//* btnnext = (AppCompatImageButton) findViewById(R.id.imagebtnnext);
        btnnext.setOnClickListener(this);*//*
        txt_pintitile = (TextView) findViewById(R.id.txt_pintitile);
        txt_pintitile.setText("Confirm 4 digit PIN");
        pinEntry = (PinEntryEditText) findViewById(R.id.password_field);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if(str.toString().equals(strpin)){
                        Register.StartActivity(context);
                    }else {
                        showSnackbar(btnnext,"Pin doest not match");
                    }
                }
            });
        }


    }*/

    @SuppressLint("ClickableViewAccessibility")
    private void intilizeview() {
        txt_pintitile = (TextView) findViewById(R.id.txt_pintitile);
        txt_pintitile.setText("Confirm 4 digit PIN");
        pinEntry = (PinEntryEditText) findViewById(R.id.password_field);
        disableShowSoftInput(pinEntry);
        showKeyboard(pinEntry);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if(str.toString().equals(strpin)){
                        Register.StartActivity(context);
                    }else {
                        showSnackbar(btnnext,"Pin doest not match");
                    }
                }
            });
        }

    }









    public static void StartActivity(Context context,Bundle bundle) {
        context.startActivity(new Intent(context, confirmpin.class));
        if (bundle != null) {
            strpin = bundle.getString("pin");
        }
    }

    @Override
    public void onClick(View view) {

    }
}
