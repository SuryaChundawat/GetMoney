package advice_natio.project.com.getmoney.Activites.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import advice_natio.project.com.getmoney.Modal.ProductList;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 8/27/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>
{
    private ArrayList<ProductList> productList;
    private Context context;

    public ProductAdapter(ArrayList<ProductList> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_row_product, parent, false);
        Log.d("Set infalter",""+v);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ProductList pr = productList.get(position);
        //holder.chkproduct.setText(pr.getProductname());
        String Amount = String.valueOf(pr.getProductAmount());
        holder.txtprice.setText(Amount);
        holder.imageView.setImageResource(pr.getQty());
        //holder.imageView.setImageBitmap(Bitmap.createBitmap(pr.getQty()));
        Log.d("TAG Adpater","prodct namme"+productList.get(position).getProductname()+"product amount"+productList.get(position).getProductAmount());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private CheckBox chkproduct;
        private AppCompatTextView txtprice,txttotleamout;
        public Button btnadd,btnSubtract;
        public ImageView imageView;
        /*https://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener*/


        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_product);
            //chkproduct = (CheckBox)itemView.findViewById(R.id.productCheck);
            txtprice =(AppCompatTextView) itemView.findViewById(R.id.txtprice);
            //txttotleamout = (TextView) itemView.findViewById(R.id.product_amount);
            ///btnadd =(Button) itemView.findViewById(R.id.btnpluse);
            //btnadd.setOnClickListener(this);
            //btnSubtract =(Button)itemView.findViewById(R.id.btnsubtract);
            //btnSubtract.setOnClickListener(this);
            //chkproduct.setOnClickListener(this);
            //txtQty.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int id  = v.getId();
            switch (id)
            {
                /*case R.id.btnpluse:
                    //TextView textView = (TextView) v.findViewById(R.id.txtproductqqty);
                    ProductList pr2 = (ProductList) textView.getTag();
                    int i  = pr2.getProductAmount();
                    i++;
                    pr2.setProductAmount(i);
                    notifyDataSetChanged();
                    break;
                case R.id.btnsubtract:
                   // TextView tv = (TextView)v.findViewById(R.id.txtproductqqty);
                    ProductList pr1 = (ProductList) tv.getTag();
                    int i1 =pr1.getProductAmount();
                    i1--;
                    pr1.setProductAmount(i1);
                    notifyDataSetChanged();
                    break;

                case R.id.productCheck:
                    CheckBox cb = (CheckBox)v;
                    ProductList pr =(ProductList) cb.getTag();
                    pr.setChecked(cb.isChecked());
                    break;*/
            }
        }

        /*public static interface IMyViewHolderClicks {
            public void onPotato(View caller);
            public void onTomato(ImageView callerImage);
        }*/

    }

    /*onBindViewHolder. @Override public void onBindViewHolder(final ItemHolder holder, int position)
{
    holder.checkBox.setOnCheckedChangeListener(null);
} holder.checkBox.setSelected(list.get(position).isSelected())‌​;
    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
        @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            list.get(holder.getAdapterPosition()).setSelected(isChecked)‌​;
        }
    }
    );*/
}
