package com.titu.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Recycle_VIew_Adapter extends RecyclerView.Adapter<Recycle_VIew_Adapter.MyViewHolder> {

    Context mContext;
    List<kategoriyalar> mData;

    public Recycle_VIew_Adapter(Context mContext, List<kategoriyalar> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.kategoriyalar,parent,false);
        MyViewHolder vHolder=new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
      holder.kategoriya.setText(mData.get(position).getKategoriya());
        Glide.with(mContext).load(mData.get(position).getImage_url()).into(holder.suraty);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView kategoriya;
        private ImageView suraty;

        public MyViewHolder(View itemView){
            super(itemView);
            kategoriya=itemView.findViewById(R.id.kategoriyasy);
            suraty=itemView.findViewById(R.id.menu_image);

        }
    }

}
