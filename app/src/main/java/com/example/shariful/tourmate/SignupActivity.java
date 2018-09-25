package com.example.shariful.tourmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
EditText Email,Password;
Button signUpBtn;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth=FirebaseAuth.getInstance();

        Email=findViewById(R.id.EmailID);
        Password=findViewById(R.id.passID);
        signUpBtn=findViewById(R.id.signBtnID);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Email.getText().toString();
                String password=Password.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(SignupActivity.this, "Enter Email Please", Toast.LENGTH_SHORT).show();
                    //email is empty
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    //password is empty

                    Toast.makeText(SignupActivity.this, "Enter password please", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                    Toast.makeText(SignupActivity.this, "Please wait.........", Toast.LENGTH_SHORT).show();

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isComplete())
                            {

                                Toast.makeText(SignupActivity.this, "Account Created SuccessFully", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });


                }

            }
        });

    }
}
