package com.example.smspoultry.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.smspoultry.Adaptor.MessageAdaptor;
import com.example.smspoultry.Pojo.CipherUtils;
import com.example.smspoultry.Pojo.MessagePojo;
import com.example.smspoultry.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowTransactional extends Fragment {

    Activity activity;
    ArrayList<MessagePojo> messagePojos=new ArrayList<MessagePojo>();

    ListView Listviewdata;
    Map<String, String> namePhoneMap = new HashMap<String, String>();

    public ShowTransactional() {
        // Required empty public constructor
     activity= getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_sale_cust_data, container, false);
        activity=getActivity();
        Listviewdata=(ListView)view.findViewById(R.id.scalesalelistview);

        if(ActivityCompat.checkSelfPermission(activity, "android.permission.READ_SMS")== PackageManager.PERMISSION_GRANTED) {
            Uri inboxURI = Uri.parse("content://sms/inbox");

            // List required columns
            String[] reqCols = new String[]{"_id", "address", "body","date"};

            // Get Content Resolver object, which will deal with Content
            // Provider
            ContentResolver cr = this.getActivity().getContentResolver();

            // Fetch Inbox SMS Message from Built-in Content Provider
            Cursor cursor = cr.query(inboxURI, reqCols, null, null, null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                String strid = cursor.getString(0).toString();
                String time = cursor.getString(3).toString();

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.parseLong(time));
                String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
                String strmob=cursor.getString(1).toString();

                String strbody = cursor.getString(2).toString();
                if(strbody.contains("<enc>"))
                {
                    strbody= CipherUtils.decryptNew(strbody.replace("<enc>",""));
                }

                cursor.moveToNext();
                MessagePojo mp=new MessagePojo();
                mp.setId(strid);
                mp.setContact(strmob);
                mp.setBody(strbody);
                mp.setDate(date);

                String patt="S";
                Pattern p1 = Pattern.compile(patt);
                Matcher m=p1.matcher(strmob);
                String bdy=strbody.toLowerCase();
                if (bdy.contains("rs")&&bdy.contains("credit")) {
                    messagePojos.add(mp);
                }
                else
                if (bdy.contains("rs")&&bdy.contains("debit")) {
                    messagePojos.add(mp);
                }
            }
        }

        MessageAdaptor ca = new MessageAdaptor(getActivity(),messagePojos,1);
        Listviewdata.setAdapter(ca);

        Listviewdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



        return view;
    }
    private void getPhoneNumbers() {

        Cursor phones = this.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        // Loop Through All The Numbers
        while (phones.moveToNext()) {

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            // Cleanup the phone number
            phoneNumber = phoneNumber.replaceAll("[()\\s-]+", "");

            // Enter Into Hash Map
            namePhoneMap.put(phoneNumber, name);

        }

        // Get The Contents of Hash Map in Log
        for (Map.Entry<String, String> entry : namePhoneMap.entrySet()) {
            String key = entry.getKey();
            Log.i("sms", "Phone :" + key);
            String value = entry.getValue();
            Log.d("sms", "Name :" + value);
        }

        phones.close();

    }
}
