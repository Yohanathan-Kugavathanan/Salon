package com.example.saloon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    List<Model> modelList=new ArrayList<>();
    RecyclerView mRecyclerView;
    //Layout manager for recycler
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    ///firestore instance
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //init firestore
        db=FirebaseFirestore.getInstance();


        //inititalize views
        mRecyclerView=findViewById(R.id.recycler_view);
        mAddBtn=findViewById(R.id.addBtn);

        //set recyclerview properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //init progress dialog
        pd=new ProgressDialog(this);

        //Show data in recycler view
        showData();

        //handle floating action button click(go to booking activity)
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, booking.class));
                finish();
            }
        });


    }

    private void showData() {
        //set title of progress dialog
        pd.setTitle("Loading Appointments...");
        //Show progress dialog
        pd.show();


        db.collection("Bookings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        //Called when data is retrieved
                        pd.dismiss();
                        //show data
                        for (DocumentSnapshot doc: task.getResult()){
                            Model model=new Model(doc.getString("id"),
                            doc.getString("Date"),
                                    doc.getString("Time"),
                            doc.getString("NumberOfAppointments"));
                            modelList.add(model);
                        }

                        //adapter
                        adapter=new CustomAdapter(ListActivity.this,modelList);
                        //Set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any arror while retreiving
                        pd.dismiss();

                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    });




    }

    public void deleteData(int index){

        //set title of progress dialog
        pd.setTitle("Your Appointment is being Deleted");
        //Show progress dialog
        pd.show();

        db.collection("Bookings").document(modelList.get(index).getId())
        .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when deleteed successfully
                        Toast.makeText(ListActivity.this, "Appointment is Deleted", Toast.LENGTH_SHORT).show();
                        //Update data
                        showData();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
/* add FloatingAction btn in this activity to (go to Main activity) to add new data
for this we will need to add design library to build.gradle*/

