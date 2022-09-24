package com.example.smspoultry;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.smspoultry.Fragments.ShowDueDates;
import com.example.smspoultry.Fragments.ShowFavourite;
import com.example.smspoultry.Fragments.ShowMessageData;
import com.example.smspoultry.Fragments.ShowOTP;
import com.example.smspoultry.Fragments.ShowSaleCustData;
import com.example.smspoultry.Fragments.ShowTransactional;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityMenu extends AppCompatActivity {
    Fragment sale_cust_data = new ShowFavourite();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = sale_cust_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         if (ActivityCompat.checkSelfPermission(ActivityMenu.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityMenu.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            // return;
        }else{
            // Write you code here if permission already given.
        }
        if (ActivityCompat.checkSelfPermission(ActivityMenu.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityMenu.this, new String[]{Manifest.permission.READ_SMS}, 1);
            // return;
        }else{
            // Write you code here if permission already given.
        }
        if (ActivityCompat.checkSelfPermission(ActivityMenu.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityMenu.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
            // return;
        }else{
            // Write you code here if permission already given.
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation1);
        fm.beginTransaction().add(R.id.main_container1, sale_cust_data, "1").commit();
       navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_fav:
                    Fragment favourite = new ShowFavourite();

                    fm.beginTransaction().replace(R.id.main_container1,favourite).commit();
                    active = favourite;
                    return true;
                case R.id.navigation_otp:
                    Fragment otp = new ShowOTP();

                    fm.beginTransaction().replace(R.id.main_container1,otp).commit();
                    active = otp;
                    return true;
                case R.id.navigation_sendSMS:
                    Intent intent=new Intent(ActivityMenu.this,SendSMSActivity.class);
                    startActivity(intent);
                    return true;
            }
               return false;
            }
    });
    }
}
