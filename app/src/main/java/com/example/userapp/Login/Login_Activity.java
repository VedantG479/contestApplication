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

import com.example.userapp.MainActivity;
import com.example.userapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

public class Login_Activity extends AppCompatActivity {
    private EditText loginEmail,loginPassword;
    private Button loginBtn;
    private TextView registerTxtbox,forgotTxtbox;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginBtn);
        loginPassword = findViewById(R.id.loginPassword);
        loginEmail = findViewById(R.id.loginEmail);
        registerTxtbox = findViewById(R.id.registerTxtbox);
        forgotTxtbox = findViewById(R.id.forgotTxtbox);
        auth = FirebaseAuth.getInstance();
        registerTxtbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,Register_Activity.class));
                finish();
            }
        });
        forgotTxtbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,Forgot_Activity.class));
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation(){
        if(loginEmail.getText().toString().isEmpty()||loginPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
        }
        else{
            loginUser();
        }
    }
    private void loginUser(){
        auth.signInWithEmailAndPassword(loginEmail.getText().toString(),loginPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login_Activity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_Activity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
        }
    }
}