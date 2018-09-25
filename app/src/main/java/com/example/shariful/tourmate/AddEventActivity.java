package com.example.shariful.tourmate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
          EditText destination,budget;
          TextView fromDate,toDate;
          Button creatBtn,shoeventBtn,UpdateBtn;


    private DatabaseReference rootRef;
    private DatabaseReference traveler;
    private DatabaseReference userRef;
    private FirebaseUser user;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        user= FirebaseAuth.getInstance().getCurrentUser();
        rootRef= FirebaseDatabase.getInstance().getReference();
        traveler=rootRef.child("Traveler");
        userRef=traveler.child("user");

       destination =findViewById(R.id.destinationID);
       budget =findViewById(R.id.budgetID);
       fromDate =findViewById(R.id.fromDateID);
       toDate=findViewById(R.id.TodateID);
       creatBtn=findViewById(R.id.createBtnID);
       shoeventBtn=findViewById(R.id.show_eventID);



       fromDate.setOnClickListener(AddEventActivity.this);
       toDate.setOnClickListener(AddEventActivity.this);


       creatBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String desti=destination.getText().toString();
               String budgt=budget.getText().toString();
               String fromD=fromDate.getText().toString();
               String toD=toDate.getText().toString();

               String id=userRef.push().getKey();

               Event event=new Event(id,desti,budgt,fromD,toD);

               userRef.child(id).setValue(event);
               Toast.makeText(AddEventActivity.this, "creat click", Toast.LENGTH_SHORT).show();
           }
       });

        shoeventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddEventActivity.this,ShowEventActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {



        if(view.getId()==R.id.fromDateID)
        {

            DatePicker datePicker=new DatePicker(this);

        int currentDay=  datePicker.getDayOfMonth();
        int currentMonth=(datePicker.getMonth())+1;
        int currentYear=datePicker.getYear();

        datePickerDialog =new DatePickerDialog(this,

                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        fromDate.setText(i2+"/"+(i1+1)+"/"+i);

                    }
                },currentYear,currentMonth,currentDay);
        datePickerDialog.show();
        }
        if(view.getId()==R.id.TodateID)
        {
            DatePicker datePicker=new DatePicker(this);

        int currentDay=  datePicker.getDayOfMonth();
        int currentMonth=(datePicker.getMonth())+1;
        int currentYear=datePicker.getYear();

        datePickerDialog =new DatePickerDialog(this,

                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        toDate.setText(i2+"/"+(i1+1)+"/"+i);

                    }
                },currentYear,currentMonth,currentDay);
        datePickerDialog.show();
        }

    }
}
