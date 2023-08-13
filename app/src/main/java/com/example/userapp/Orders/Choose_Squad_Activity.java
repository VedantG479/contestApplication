package com.example.userapp.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.userapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Choose_Squad_Activity extends AppCompatActivity {
    private String referenceID;
    private EditText player1ID,player2ID,player3ID,player4ID,player1N,player2N,player3N,player4N;
    private Button joinBtn;
    private DatabaseReference reference,reference2;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_squad);
        referenceID = getIntent().getStringExtra("ref_no");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        player1ID = findViewById(R.id.player1ID);
        player2ID = findViewById(R.id.player2ID);
        player3ID = findViewById(R.id.player3ID);
        player4ID = findViewById(R.id.player4ID);
        player1N = findViewById(R.id.player1N);
        player2N = findViewById(R.id.player2N);
        player3N = findViewById(R.id.player3N);
        player4N = findViewById(R.id.player4N);
        joinBtn = findViewById(R.id.joinBtn);

        reference = FirebaseDatabase.getInstance().getReference().child("TotalOrders");
        reference2 = FirebaseDatabase.getInstance().getReference().child("TotalOrders");

        getSquad();

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinSquad();
            }
        });
    }
    private void joinSquad(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap hp = new HashMap();
                hp.put("p1ID",player1ID.getText().toString());
                hp.put("p2ID",player2ID.getText().toString());
                hp.put("p3ID",player3ID.getText().toString());
                hp.put("p4ID",player4ID.getText().toString());
                hp.put("p1N",player1N.getText().toString());
                hp.put("p2N",player2N.getText().toString());
                hp.put("p3N",player3N.getText().toString());
                hp.put("p4N",player4N.getText().toString());

                reference.child(referenceID).child(user.getUid()).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(Choose_Squad_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getSquad(){
        reference2.child(referenceID).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String p1ID= snapshot.child("p1ID").getValue(String.class);
                String p2ID= snapshot.child("p2ID").getValue(String.class);
                String p3ID= snapshot.child("p3ID").getValue(String.class);
                String p4ID= snapshot.child("p4ID").getValue(String.class);
                String p1N= snapshot.child("p1N").getValue(String.class);
                String p2N= snapshot.child("p2N").getValue(String.class);
                String p3N= snapshot.child("p3N").getValue(String.class);
                String p4N= snapshot.child("p4N").getValue(String.class);

                player1ID.setText(p1ID);
                player2ID.setText(p2ID);
                player3ID.setText(p3ID);
                player4ID.setText(p4ID);
                player1N.setText(p1N);
                player2N.setText(p2N);
                player3N.setText(p3N);
                player4N.setText(p4N);

                if(!p1ID.equals("")||!p2ID.equals("")||!p3ID.equals("")||!p4ID.equals("")||!p1N.equals("")||!p2N.equals("")||!p3N.equals("")||!p4N.equals("")) {
                    joinBtn.setText("Update");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}