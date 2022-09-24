package com.example.smspoultry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smspoultry.Pojo.CipherUtils;

public class SendSMSActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    Button btsend;
    EditText et1,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_s_m_s);
        btsend=findViewById(R.id.btsend);
        et1=findViewById(R.id.etcontact);
        et2=findViewById(R.id.etbody);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=et2.getText().toString();
                String ss=CipherUtils.encryptNew(msg);
                msg= "<enc>"+ss;
                Toast.makeText(SendSMSActivity.this, "sms"+ss, Toast.LENGTH_SHORT).show();

                sendSMS(et1.getText().toString(),msg);
                Toast.makeText(SendSMSActivity.this, "sms sent", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendSMS(String mob1,String msg) {
        //message = txtMessage.getText().toString();
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(mob1,null,msg,null,null);
        // smsManager.sendTextMessage(mob2,null,"app demo",null,null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
    }
}