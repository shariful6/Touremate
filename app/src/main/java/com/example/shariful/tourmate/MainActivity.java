package com.example.shariful.tourmate;

import android.annotation.SuppressLint;
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

public class MainActivity extends AppCompatActivity {
  Button mSignup,mLogin;
  EditText mEmail,mPassword;
    private FirebaseAuth firebaseAuth;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

        mEmail=findViewById(R.id.email_loginID);
        mPassword=findViewById(R.id.pass_loginID);

        mSignup=findViewById(R.id.signupID);
        mLogin=findViewById(R.id.loginID);




        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Enter Email Please", Toast.LENGTH_SHORT).show();
                    //email is empty
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    //password is empty

                    Toast.makeText(MainActivity.this, "Enter password please", Toast.LENGTH_SHORT).show();
                    return;
                }

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                               Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                               startActivity(intent);
                            }
                            else
                            {

                                Toast.makeText(MainActivity.this, "Failed to login !!\n Please check Your Email& Password ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

            }
        });

    }

}
