package com.example.saloon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class booking extends AppCompatActivity {

    //Views
    EditText mDatEt, mTimEt,mNumbrEt;
    Button mSaveBtn,mListBtn;

    //progress dialog
    ProgressDialog pd;

    //Firestore instance
    FirebaseFirestore db ;

    String pId,pDat,pTim,pNumbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
    //actionbar and its title

        //add internet permission in manifest

    //initialize views with its xml
        mDatEt=findViewById(R.id.datEt);
        mTimEt=findViewById(R.id.timEt);
        mNumbrEt=findViewById(R.id.numbrEt);
        mSaveBtn=findViewById(R.id.saveBtn);
        mListBtn=findViewById(R.id.listBtn);

        /*if we came here after clicking Update option(from AlertDialog of ListActivity)
        *then get data(id,title,description)from intent and set to edittext
        * */
        final Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            //Update
            mSaveBtn.setText("Update");
            //get data
            pId=bundle.getString("pId");
            pDat=bundle.getString("pDat");
            pTim=bundle.getString("pTim");
            pNumbr=bundle.getString("pNumbr");

            //set data
            mDatEt.setText(pDat);
            mTimEt.setText(pTim);
            mNumbrEt.setText(pNumbr);
        }
        else{
            //New data
            mSaveBtn.setText("Submit");
        }


    //Progress dialog
        pd=new ProgressDialog(this);

        //firestore
        db=FirebaseFirestore.getInstance();

        //click button to upload data
        /*if we came here after clicking Update option(from AlertDialog of ListActivity)
        *then get the data(title,description) from EditText,id from intent,
        * and update existing data on the basis of id
        * */

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1=getIntent().getExtras();
                if(bundle!=null){
                    //Updating
                    //input data
                    String id=pId;
                    String dat=mDatEt.getText().toString().trim();
                    String tim=mTimEt.getText().toString().trim();
                    String numbr=mNumbrEt.getText().toString().trim();


                    //function call to update data
                    updateData(id,dat,tim,numbr);


                }
                else{
                    //Adding new

                    //input data
                    String dat=mDatEt.getText().toString().trim();
                    String tim=mTimEt.getText().toString().trim();
                    String numbr=mNumbrEt.getText().toString().trim();

                    //function call to upload data
                    uploadData(dat,tim,numbr);


                }
                }
        });
        // Click btn to start list activity
        mListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(booking.this,ListActivity.class));
                finish();
            }
        });





    }

    private void updateData(String id, String tim, String dat, String numbr) {
        //set title to progress bar
        pd.setTitle("Your Appointment is being Updated");
        //show progress bar when user click save button
        pd.show();

        db.collection("Bookings").document(id)
                .update("Date",dat,"Time",tim,"NumberOfAppointments",numbr )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when Updated successfully
                    pd.dismiss();
                        Toast.makeText(booking.this, "Appointment is Updated", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is an error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(booking.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void uploadData(String dat, String tim,String numbr) {
    //set title to progress bar
        pd.setTitle("Your Appointment is being Submitted");
        //show progress bar when user click save button
        pd.show();
        //random id for each data to be stored
        String id= UUID.randomUUID().toString();

        Map<String, Object> doc=new HashMap<>();
        doc.put("id",id); //id of data
        doc.put("Date",dat);
        doc.put("Time",tim);
        doc.put("NumberOfAppointments",numbr);

        //addd this data
        db.collection("Bookings").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called whem data is added successfully

                        pd.dismiss();
                        Toast.makeText(booking.this, "Appointment Submitted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any error while uploading

                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(booking.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
