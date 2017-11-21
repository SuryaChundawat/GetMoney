package advice_natio.project.com.getmoney.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.BaseActivity.PinEntryEditText;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 10/29/2017.
 */

public class setuppin extends BaseActivity implements View.OnClickListener
{
    private AppCompatImageButton btnnext;
    private PinEntryEditText pinEntry;
    private Context context;
    private String TAG="setuppin.class";
    private TextView txt_pintitile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmsetuppin);
        context = this;
        intilizeview();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void intilizeview() {
        txt_pintitile = (TextView) findViewById(R.id.txt_pintitile);
        txt_pintitile.setText("Enter 4 digit PIN");
        pinEntry = (PinEntryEditText) findViewById(R.id.password_field);
        disableShowSoftInput(pinEntry);
        showKeyboard(pinEntry);
       /* pinEntry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showKeyboard(pinEntry);
                return false;
            }
        });*/

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if(str.length()==4) {
                        if (isOnline(context)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("pin",str.toString().trim());
                            Log.d(TAG,"Otp Key"+str);
                            confirmpin.StartActivity(context,bundle);
                        }
                    }
                }
            });
        }

    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,setuppin.class));
    }

    @Override
    public void onClick(View view) {

    }
}
