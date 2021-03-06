package com.example.saloon;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Mainfeedback extends AppCompatActivity {
    //Views
    EditText mTitleEt, mCommentEt,mId;
    Button mSaveBtn,mListBtn;

    //progress dialog
    ProgressDialog pd;

    //Firestore instance
    FirebaseFirestore db ;

    String pTitle,pComment,pId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);



        //add internet permission in manifest

        //initialize views with its xml
        mTitleEt=findViewById(R.id.titleEt);
        mCommentEt=findViewById(R.id.commentEt);
        mSaveBtn=findViewById(R.id.saveBtn);
        mListBtn=findViewById(R.id.listBtn);

        /*if we came here after clicking Update option(from AlertDialog of ListActivity)
         *then get data(id,title,description)from intent and set to edittext
         * */
        final Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            //Update
            mSaveBtn.setText("Update");
            //get data
            pId=bundle.getString("pId");
            pTitle=bundle.getString("pTitle");
            pComment=bundle.getString("pComment");

            //set data
             mId.setText(pId);
             mTitleEt.setText(pTitle);
             mCommentEt.setText(pComment);

        }
        else{
            //New data
            mSaveBtn.setText("Submit");


        }
        //Progress dialog
        pd=new ProgressDialog(this);

        //firestore
        db= FirebaseFirestore.getInstance();
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
                    String title=mTitleEt.getText().toString().trim();
                    String comment=mCommentEt.getText().toString().trim();



                    //function call to update data
                    updateData(id,title,comment);


                }
                else{
                    //Adding new

                    //input data



                    String title=mTitleEt.getText().toString().trim();
                    String comment=mCommentEt.getText().toString().trim();

                    //function call to upload data
                    uploadData(title,comment);


                }
            }


        });
        // Click btn to start list activity
        mListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainfeedback.this,ListActivity.class));
                finish();
            }
        });





    }

    private void updateData(String id, String title, String comment) {
        //set title to progress bar
        pd.setTitle("Your Appointment is being Updated");
        //show progress bar when user click save button
        pd.show();

        db.collection("Bookings").document(id)
                .update("Title",title,"Comment",comment )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when Updated successfully
                        pd.dismiss();
                        Toast.makeText(Mainfeedback.this, "Appointment is Updated", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is an error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(Mainfeedback.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void uploadData(String id, String title, String comment) {
        //set title to progress bar
        pd.setTitle("Your Appointment is being Submitted");
        //show progress bar when user click save button
        pd.show();
        //random id for each data to be stored
        String id= UUID.randomUUID().toString();

        Map<String, Object> doc=new HashMap<>();
        doc.put("id",id); //id of data
        doc.put("Title", title);
        doc.put("Comment", Comment);



        //addd this data
        db.collection("Bookings").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called whem data is added successfully

                        pd.dismiss();
                        Toast.makeText(Mainfeedback.this, "Appointment Submitted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any error while uploading

                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(Mainfeedback.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
