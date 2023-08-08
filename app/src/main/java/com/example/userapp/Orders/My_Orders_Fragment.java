package com.example.userapp.Orders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.userapp.R;
import com.example.userapp.Tickets.Match_Adapter;
import com.example.userapp.Tickets.Match_Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class My_Orders_Fragment extends Fragment {
    private RecyclerView orderRecycler;
    private ProgressBar progressBar;
    private ArrayList<Match_Data> list;
    private Order_Adapter adapter;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my__orders_, container, false);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        orderRecycler = view.findViewById(R.id.orderRecycler);
        progressBar = view.findViewById(R.id.progressBar);
        reference = FirebaseDatabase.getInstance().getReference().child("Orders").child(user.getUid());
        orderRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        orderRecycler.setHasFixedSize(true);

        getData();
        return view;
    }
    private void getData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Match_Data data = snapshot1.getValue(Match_Data.class);
                    list.add(0,data);
                }
                adapter = new Order_Adapter(getContext(),list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                orderRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(),"No Data Found"+error.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}