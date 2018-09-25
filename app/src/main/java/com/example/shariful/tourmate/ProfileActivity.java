package com.example.shariful.tourmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
   Button mAddEvent,mShowEvent,mNearby,mExpense,mAddMoment,mShowMoment,mWeather;

   private DatabaseReference rootRef;
   private DatabaseReference traveler;
   private DatabaseReference userRef;
   private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user= FirebaseAuth.getInstance().getCurrentUser();
        rootRef= FirebaseDatabase.getInstance().getReference();
        traveler=rootRef.child("Traveler");
        userRef=traveler.child("user");

        mAddEvent=findViewById(R.id.Add_eventID);
        mShowEvent=findViewById(R.id.show_eventID);

        mNearby=findViewById(R.id.nearbyID);

        mExpense=findViewById(R.id.travel_expenseID);

        mAddMoment=findViewById(R.id.add_momentID);
        mShowMoment=findViewById(R.id.show_momentID);

        mWeather=findViewById(R.id.weatherID);




        //For adding event
        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Add event clicked", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(ProfileActivity.this,AddEventActivity.class);
                startActivity(intent);

            }
        });

        mExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ProfileActivity.this,ExpenseActivity.class);
                startActivity(intent);


            }
        });

     mNearby.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String time = DateFormat.getDateTimeInstance().format(new Date());

        Toast.makeText(ProfileActivity.this, "Date and time is :  "+time, Toast.LENGTH_SHORT).show();

    }
   });




    }
}
