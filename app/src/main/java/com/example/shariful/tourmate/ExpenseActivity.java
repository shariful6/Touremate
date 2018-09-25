package com.example.shariful.tourmate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {
    LinearLayout Expense_Entry_Field;

    ListView expense_listView;
     Button AddExpenseBtn,HideBtn;
     EditText Expense_Details,Expense_Tk;

     Expense expense_obj;

    private List<Expense> expense_list=new ArrayList<Expense>();
    List<String> item=new ArrayList<>();


    private DatabaseReference rootRef;
    private DatabaseReference traveler;
    private DatabaseReference userRef;
    private DatabaseReference expense;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        expense_listView=findViewById(R.id.Expense_listviewID);

        AddExpenseBtn=findViewById(R.id.Save_ExpenseID);
        HideBtn=findViewById(R.id.hideID);

        Expense_Details=findViewById(R.id.Ex_detailsID);
        Expense_Tk=findViewById(R.id.Ex_AmountID);
        Expense_Entry_Field=findViewById(R.id.expenseAddSectionID);

        user= FirebaseAuth.getInstance().getCurrentUser();
        rootRef= FirebaseDatabase.getInstance().getReference();
        traveler=rootRef.child("Traveler");
        userRef=traveler.child("user");
        expense=userRef.child("Expense");



        AddExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expense_Entry_Field.setVisibility(View.VISIBLE);
                HideBtn.setVisibility(View.VISIBLE);

               String time = DateFormat.getDateTimeInstance().format(new Date());
              // long time=System.currentTimeMillis();

               String Expense_Dt=Expense_Details.getText().toString();
               String Expense_Taka=Expense_Tk.getText().toString();

                if(Expense_Dt.length()!=0||Expense_Taka.length()!=0)
                {

                    String id=expense.push().getKey();

                     Expense expense_m=new Expense(id,time,Expense_Dt,Expense_Taka);

                    expense.child(id).setValue(expense_m).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ExpenseActivity.this, "Save Successfully !", Toast.LENGTH_SHORT).show();

                            Expense_Details.setText("");
                            Expense_Tk.setText("");
                            HideBtn.setVisibility(View.GONE);
                            Expense_Entry_Field.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    Toast.makeText(ExpenseActivity.this, "Please Enter All Field !!", Toast.LENGTH_SHORT).show();
                }


            }
        });


        expense.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expense_list.clear();
                item.clear();

                for(DataSnapshot d: dataSnapshot.getChildren())
                {


                    expense_obj=d.getValue(Expense.class);
                    expense_list.add(expense_obj);
                    item.add("     "+expense_obj.getTime()+"\n\n"+"  "+expense_obj.getDetails().toString()+"    "+expense_obj.getTaka().toString()+" tk\n");

                }


                ArrayAdapter<String> adapter=new ArrayAdapter<>(ExpenseActivity.this,R.layout.work_list,R.id.textViewID,item);
                expense_listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        expense_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Expense e=expense_list.get(i);
                String expense_id=e.getId();
                expense.child(expense_id).removeValue();
                Toast.makeText(ExpenseActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        HideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expense_Entry_Field.setVisibility(View.GONE);
                HideBtn.setVisibility(View.GONE);

            }
        });



    }
}
