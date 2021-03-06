package com.example.saloon;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mDatTv,mTimTv,mNumbrTv;
    View mView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

    mView=itemView;

    //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
    //item long click istener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
            mClickListener.onItemLongClick(v, getAdapterPosition());

            return true;
            }
        });
    // initialize views with model layout.xml
        mDatTv=itemView.findViewById(R.id.rDatTv);
        mTimTv=itemView.findViewById(R.id.rTimTv);
        mNumbrTv=itemView.findViewById(R.id.rNumbrTv);
    }


    private ViewHolder.ClickListener mClickListener;


    //interface for click listener
    public interface ClickListener{

        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);


    }
            public void setOnclickListener(ViewHolder.ClickListener clickListener){

            mClickListener=clickListener;

            }




}
