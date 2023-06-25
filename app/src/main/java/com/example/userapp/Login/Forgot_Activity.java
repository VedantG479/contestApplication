package com.example.userapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Activity extends AppCompatActivity {
    private EditText forgotEmail;
    private TextView loginTxtbox,registerTxtbox;
    private Button submitBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        forgotEmail = findViewById(R.id.forgotEmail);
        loginTxtbox = findViewById(R.id.loginTxtbox);
        registerTxtbox = findViewById(R.id.registerTxtbox);
        submitBtn = findViewById(R.id.submitBtn);
        forgotEmail = findViewById(R.id.forgotEmail);
        auth = FirebaseAuth.getInstance();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forgotEmail.getText().toString().isEmpty()){
                    Toast.makeText(Forgot_Activity.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendEmail();
                }
            }
        });
    }

    private void sendEmail(){
        auth.sendPasswordResetEmail(forgotEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot_Activity.this,"Email Sent",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forgot_Activity.this,Login_Activity.class));
                    finish();
                }
            }
        });
    }
}