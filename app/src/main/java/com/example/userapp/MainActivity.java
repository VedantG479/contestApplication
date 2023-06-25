package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.userapp.Login.Login_Activity;
import com.example.userapp.Login.Register_Activity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout navDrawer;
    private NavigationView navView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navDrawer=findViewById(R.id.navDrawer);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        navView=findViewById(R.id.navView);
        toolbar=findViewById(R.id.toolbar);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user==null){
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
            finish();
        }
    }
}