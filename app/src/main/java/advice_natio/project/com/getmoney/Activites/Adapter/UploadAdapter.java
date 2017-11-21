package advice_natio.project.com.getmoney.Activites.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import advice_natio.project.com.getmoney.Activites.UploadDetaills;
import advice_natio.project.com.getmoney.R;

/**
 * Created by Surya Chundawat on 8/19/2017.
 */

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.ViewHolder>
{
    private  List<String> uploadPic;
    private Context mContext;

    public UploadAdapter(Context context,List<String> uploadPic)
    {
        this.uploadPic=uploadPic;
        this.mContext=context;
    }


    @Override
    public UploadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_rowlist_upload, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final UploadAdapter.ViewHolder holder, final int position)
    {
        Random rnd = new Random();
        int currentStrokeColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.image.setBackgroundColor(currentStrokeColor);
        holder.text.setText(uploadPic.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*UploadDetaills.setScan(position,(holder).image);*/
            }
        });

    }


    @Override
    public int getItemCount() {
        return uploadPic.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView)
        {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
