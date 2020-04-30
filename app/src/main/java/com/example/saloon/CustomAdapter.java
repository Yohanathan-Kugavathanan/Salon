package com.example.saloon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       //Inflate layout

        View itemView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout,viewGroup,false);

        ViewHolder viewHolder=new ViewHolder(itemView);
        //Handle item clicks here
        viewHolder.setOnclickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user click item


            //show data in toast on clicking
            String dat=modelList.get(position).getDat();
            String tim=modelList.get(position).getTim();
            String numbr=modelList.get(position).getNumbr();

                Toast.makeText(listActivity, dat+"\n"+tim+"\n"+numbr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                //this will ne called when user long click item


                //Creating Alert dialog
                AlertDialog.Builder builder=new AlertDialog.Builder(listActivity);

                //option to display in dialog
                String[] options={"Update Appointment", "Delete Appointment"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            //Update is clicked

                            //get data
                            String id=modelList.get(position).getId();
                            String dat=modelList.get(position).getDat();
                            String tim=modelList.get(position).getTim();
                            String numbr=modelList.get(position).getNumbr();

                            //intent to start activity
                            Intent intent=new Intent(listActivity,booking.class);
                            //put data in internet
                            intent.putExtra("pId",id);
                            intent.putExtra("pDat",dat);
                            intent.putExtra("pTim",tim);
                            intent.putExtra("pNumbr",numbr);

                            //start activity
                            listActivity.startActivity(intent);
                        }
                        if(which==1){
                            //delete is clicked
                        listActivity.deleteData(position);


                        }
                    }
                }).create().show();
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //bindViews  //set data
        viewHolder.mDatTv.setText(modelList.get(i).getDat());
        viewHolder.mTimTv.setText(modelList.get(i).getTim());
        viewHolder.mNumbrTv.setText(modelList.get(i).getNumbr());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
