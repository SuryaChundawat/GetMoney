package advice_natio.project.com.getmoney.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 8/20/2017.
 */

public class Encashment extends BaseActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancashment);
        InitiStatus();
        InitiToolbar((Toolbar)findViewById(R.id.toolbar),getResources().getString(R.string.title_encashmentactivity));
    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,Encashment.class));
    }
}
