package advice_natio.project.com.getmoney.Modal;

import android.widget.CheckBox;

/**
 * Created by Surya Chundawat on 8/27/2017.
 */


public  class ProductList
{

    public String productname;
    public int productAmount;
    public boolean Checked=false;
    public int Qty;


    public ProductList(String productname, int Amout,boolean checked,int qty) {
        this.productname=productname;
        this.productAmount=Amout;
        this.Checked=checked;
        this.Qty = qty;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }


    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
