package com.example.smspoultry;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.smspoultry.Fragments.ShowDueDates;
import com.example.smspoultry.Fragments.ShowFavourite;
import com.example.smspoultry.Fragments.ShowMessageData;
import com.example.smspoultry.Fragments.ShowSaleCustData;
import com.example.smspoultry.Fragments.ShowTransactional;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    Fragment sale_cust_data = new ShowSaleCustData();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = sale_cust_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            // return;
        }else{
            // Write you code here if permission already given.
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 1);
            // return;
        }else{
            // Write you code here if permission already given.
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
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
                case R.id.navigation_sale:

                   /* ProgressDialog pd=new ProgressDialog(MainActivity.this);
                    pd.setTitle("Loading");
                    pd.setMessage("connecting to weight scale..");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                    */
                    Toast.makeText(MainActivity.this, "sms read", Toast.LENGTH_SHORT).show();
                    Fragment Showmessage = new ShowMessageData();

                    fm.beginTransaction().replace(R.id.main_container1,Showmessage).commit();
                    active = Showmessage;



                    return true;
                case R.id.navigation_recovery:
                    Fragment sale_cust_data1 = new ShowSaleCustData();

                    fm.beginTransaction().replace(R.id.main_container1,sale_cust_data1).commit();
                    active = sale_cust_data1;
                    return true;
                case R.id.navigation_transactional:
                    Fragment show_transactional = new ShowTransactional();

                    fm.beginTransaction().replace(R.id.main_container1,show_transactional).commit();
                    active = show_transactional;
                    return true;
                case R.id.navigation_duedates:
                    Fragment showDueDates = new ShowDueDates();

                    fm.beginTransaction().replace(R.id.main_container1,showDueDates).commit();
                    active = showDueDates;
                    return true;
                case R.id.navigation_fav:
                   // Fragment favourite = new ShowFavourite();

                   // fm.beginTransaction().replace(R.id.main_container1,favourite).commit();
                   // active = favourite;

                    Intent intent=new Intent(MainActivity.this, ActivityMenu.class);
                   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                    return true;
               // case R.id.navigation_otp:
                   // Fragment otp = new ShowFavourite();

                   // fm.beginTransaction().replace(R.id.main_container1,otp).commit();
                   // active = otp;
                   // return true;
            }
               return false;
            }
    });
    }
}
