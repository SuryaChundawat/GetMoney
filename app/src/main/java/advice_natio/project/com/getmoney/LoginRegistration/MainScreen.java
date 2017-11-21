package advice_natio.project.com.getmoney.LoginRegistration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;

import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.R;


/**
 * Created by Chari on 8/8/2017.
 */

public class MainScreen extends BaseActivity implements View.OnClickListener
{
    private AppCompatButton btnLogin,btnRegister;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        context = this;
        IniView();
    }

    private void IniView() {
        btnLogin = (AppCompatButton) findViewById(R.id.btnlogin);
        btnRegister = (AppCompatButton) findViewById(R.id.btnregistration);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }


    public static void startScreen(Context context) {
        context.startActivity(new Intent(context, MainScreen.class));
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
        }

        if (view == btnRegister) {
            RegisterUser.StartActivity(context);
        }

    }
}
