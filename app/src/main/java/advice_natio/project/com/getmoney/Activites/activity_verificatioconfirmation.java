package advice_natio.project.com.getmoney.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 10/29/2017.
 */

public class activity_verificatioconfirmation extends AppCompatActivity implements View.OnClickListener
{
    private AppCompatButton btnsignup;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationconfirmation);
        initview();
    }

    private void initview() {
        context = this;
        btnsignup = (AppCompatButton) findViewById(R.id.btn_setuppin);
        btnsignup.setOnClickListener(this);

    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,activity_verificatioconfirmation.class));
    }

    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.btn_setuppin:
                setuppin.StartActivity(context);
                break;


        }

    }
}
