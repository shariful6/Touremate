package com.example.shariful.tourmate;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowEventActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    EditText destination,budget;
    TextView fromDate,toDate;
    Button updateBt;
    LinearLayout updatSection;
    private DatePickerDialog datePickerDialog;


    private List<Event> events=new ArrayList<>();
    List<String> list=new ArrayList<>();
    private ArrayAdapter<String> adapter;
   // private ArrayAdapter<String> adapter2;
    Event event;

     //  private List<String> eventName=new ArrayList<>();
   //   private List<String> evenBudget=new ArrayList<>();
//    private List<String> evenDateFrom=new ArrayList<>();
//    private List<String> eventDateTo=new ArrayList<>();



    private DatabaseReference rootRef;
    private DatabaseReference traveler;
    private DatabaseReference userRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        user= FirebaseAuth.getInstance().getCurrentUser();
        rootRef= FirebaseDatabase.getInstance().getReference();
        traveler=rootRef.child("Traveler");
        userRef=traveler.child("user");

        listView=findViewById(R.id.listviewID);


        destination =findViewById(R.id.destID);
        budget =findViewById(R.id.budID);
        fromDate =findViewById(R.id.fromID);
        toDate=findViewById(R.id.ToID);
        updateBt=findViewById(R.id.UpID);
       updatSection=findViewById(R.id.UpdateSectionID);


        userRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              list.clear();
               events.clear();
              for(DataSnapshot d: dataSnapshot.getChildren())
              {


                event=d.getValue(Event.class);
                events.add(event);
               list.add("     "+event.getDestination().toString()+"\n\n"+"From                          To \n"+event.getFromDate().toString()+"             "+event.getToDate().toString()+"\n\n"+"          Est Budget : "+event.getBudget().toString());

              }


              ArrayAdapter<String> adapter=new ArrayAdapter<>(ShowEventActivity.this,R.layout.work_list,R.id.textViewID,list);
              listView.setAdapter(adapter);
              adapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Event event=events.get(i);
                String eventid=event.getId();
                userRef.child(eventid).removeValue();
                Toast.makeText(ShowEventActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                updatSection.setVisibility(View.VISIBLE);
                Event event=events.get(i);
                String eventid=event.getId();

                updateBt.setTag(eventid);

                destination.setText(event.getDestination());
                budget.setText(event.getBudget());
                fromDate.setText(event.getFromDate());
                toDate.setText(event.getToDate());

                fromDate.setOnClickListener(ShowEventActivity.this);
                toDate.setOnClickListener(ShowEventActivity.this);

                return true;
            }
        });

      updateBt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String desti=destination.getText().toString();
              String budgt=budget.getText().toString();
              String fromD=fromDate.getText().toString();
              String toD=toDate.getText().toString();

              String deletID= (String) updateBt.getTag();
              Event upevent=new Event(deletID,desti,budgt,fromD,toD);
              userRef.child(deletID).setValue(upevent).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      Toast.makeText(ShowEventActivity.this, "Updated !", Toast.LENGTH_SHORT).show();
                  }
              });
              updatSection.setVisibility(View.GONE);

          }
      });

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.fromID)
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
        if(view.getId()==R.id.ToID)
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
