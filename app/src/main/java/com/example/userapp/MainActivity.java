package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.Login.Login_Activity;
import com.example.userapp.Login.Register_Activity;
import com.example.userapp.Orders.My_Orders_Fragment;
import com.example.userapp.Tickets.Afternoon_Fragment;
import com.example.userapp.Tickets.Evening_Fragment;
import com.example.userapp.Tickets.Morning_Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout navDrawer;
    private NavigationView navView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private View header;
    private CardView morningCard,afternoonCard,eveningCard,myOrdersCard;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navDrawer=findViewById(R.id.navDrawer);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        navView=findViewById(R.id.navView);
        toolbar=findViewById(R.id.toolbar);

        morningCard=findViewById(R.id.morningCard);
        morningCard.setOnClickListener(this);
        eveningCard=findViewById(R.id.eveningCard);
        eveningCard.setOnClickListener(this);
        afternoonCard=findViewById(R.id.afternoonCard);
        afternoonCard.setOnClickListener(this);
        myOrdersCard=findViewById(R.id.my_OrdersCard);
        myOrdersCard.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new ActionBarDrawerToggle(this,navDrawer,toolbar,R.string.Start,R.string.Close);
        navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navView.bringToFront();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Toast.makeText(MainActivity.this,"Profile",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.my_Orders:
                        Toast.makeText(MainActivity.this,"My Orders",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.help:
                        Toast.makeText(MainActivity.this,"Help",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        header = navView.getHeaderView(0);
        if(user!=null){
            ImageView usrImage = header.findViewById(R.id.userProfilePic);
            Picasso.get().load(user.getPhotoUrl()).placeholder(getDrawable(R.drawable.user)).into(usrImage);

            TextView usrName = header.findViewById(R.id.userName);
            usrName.setText(user.getDisplayName());

            TextView usrEmail = header.findViewById(R.id.userEmail);
            usrEmail.setText(user.getEmail());

            Button logout = header.findViewById(R.id.logoutBtn);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this,Login_Activity.class));
                    finish();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user==null){
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.morningCard:
                Morning_Fragment morning_fragment = new Morning_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,morning_fragment).addToBackStack("home").commit();
                break;
            case R.id.afternoonCard:
                Afternoon_Fragment afternoon_fragment = new Afternoon_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,afternoon_fragment).addToBackStack("home").commit();
                break;
            case R.id.eveningCard:
                Evening_Fragment evening_fragment = new Evening_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,evening_fragment).addToBackStack("home").commit();
                break;
            case R.id.my_OrdersCard:
                My_Orders_Fragment my_orders_fragment = new My_Orders_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,my_orders_fragment).addToBackStack("home").commit();
                break;
        }
    }
}