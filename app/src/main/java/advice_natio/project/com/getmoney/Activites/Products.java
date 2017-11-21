package advice_natio.project.com.getmoney.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import advice_natio.project.com.getmoney.Activites.Adapter.ProductAdapter;
import advice_natio.project.com.getmoney.Activites.Adapter.UploadAdapter;
import advice_natio.project.com.getmoney.BaseActivity.BaseActivity;
import advice_natio.project.com.getmoney.Modal.ProductList;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 8/19/2017.
 */

public class Products extends BaseActivity implements View.OnClickListener
{
    private Context context;
    private RecyclerView ListView;
    public ArrayList<ProductList> arrayList = new ArrayList<>();
    private AppCompatButton btnBuy;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        InitiStatus();
        InitiToolbar((Toolbar)findViewById(R.id.toolbar),getResources().getString(R.string.title_productdetails));
        Init();
    }

    private void Init()
    {
        ListView  = (RecyclerView) findViewById(R.id.recylerview);
        //btnBuy = (AppCompatButton) findViewById(R.id.Buy);
        //btnBuy.setOnClickListener(this);
        ProductList productList ;
        productList = new ProductList("Item one",50,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item two",40,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item three",2000,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item four",600,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item five",700,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item six",800,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item seven",30,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item mobile",5000,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item pizza",46,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item burger",70,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);
        productList = new ProductList("Item coffe",10,false,R.mipmap.ic_placeholder);
        arrayList.add(productList);

        context= this;
        ProductAdapter productAdapter = new ProductAdapter(arrayList,context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        ListView.setLayoutManager(mLayoutManager);
        ListView.setItemAnimator(new DefaultItemAnimator());
        ListView.setAdapter(productAdapter);
    }

    public static void StartActivity(Context context) {
        context.startActivity(new Intent(context,Products.class));
    }


    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId)
        {
            case R.id.Buy:
                Encashment.StartActivity(context);
                break;
        }
    }
}
