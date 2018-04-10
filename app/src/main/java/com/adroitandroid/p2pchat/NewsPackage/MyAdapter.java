package com.adroitandroid.p2pchat.NewsPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adroitandroid.p2pchat.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by him on 3/18/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<News> data;

    public MyAdapter(Context context, ArrayList<News> data) {
        this.context=context;
        this.data=data;
        layoutInflater= LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.news_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final News info=data.get(position);
        holder.heading.setText(data.get(position).getTitle());
        Glide.with(context)
                .load(data.get(position).getImageURl())
                .override(60, 60)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(holder.imageView);
        holder.detail.setText(data.get(position).getContent());
        holder.source.setText(data.get(position).getSource());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView heading,detail,source;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            heading= (TextView) itemView.findViewById(R.id.headline_text);
            detail= (TextView) itemView.findViewById(R.id.info_text);
            imageView= (ImageView) itemView.findViewById(R.id.news_image);
            source= (TextView) itemView.findViewById(R.id.source_text);
        }
    }

}
