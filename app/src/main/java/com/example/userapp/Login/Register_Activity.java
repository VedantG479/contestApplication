package com.example.userapp.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.MainActivity;
import com.example.userapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Activity extends AppCompatActivity {
    private EditText registerName, registerEmail, registerPassword;
    private CircleImageView registerImage;
    private TextView loginTxtbox;
    private Button registerBtn;
    private final int REQ = 1;
    private Bitmap bitmap;
    private FirebaseAuth auth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerEmail = findViewById(R.id.registerEmail);
        registerImage = findViewById(R.id.registerImage);
        loginTxtbox = findViewById(R.id.loginTxtbox);
        registerBtn = findViewById(R.id.registerBtn);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("User Images");

        loginTxtbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Activity .this, Login_Activity.class));
                finish();
            }
        });
        registerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && requestCode==RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            registerImage.setImageBitmap(bitmap);
        }
    }

    private void checkValidation(){
        String name = registerName.getText().toString();
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        
        if(name.isEmpty()||email.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"Please fill all the forms",Toast.LENGTH_SHORT).show();
        }
        else{
            registerUser();
        }
    }

    private void registerUser() {
        auth.createUserWithEmailAndPassword(registerEmail.getText().toString(),registerPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register_Activity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    uploadImage();
                }
                else{
                    Toast.makeText(Register_Activity.this, "Error: "+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadImage() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        byte[] finalImage = baos.toByteArray();

        final StorageReference filepath;
        filepath = storageReference.child(uid+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        updateUser(uri);
                    }
                });
            }
        });
    }

    private void updateUser(Uri uri){
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(registerName.getText().toString()).setPhotoUri(uri).build();
        auth.getCurrentUser().updateProfile(changeRequest);
        auth.signOut();
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();
    }
}