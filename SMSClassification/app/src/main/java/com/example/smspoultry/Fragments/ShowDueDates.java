package com.example.smspoultry.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.smspoultry.Adaptor.MessageAdaptor;
import com.example.smspoultry.Pojo.CipherUtils;
import com.example.smspoultry.Pojo.MessagePojo;
import com.example.smspoultry.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowDueDates extends Fragment {

    Activity activity;
    ArrayList<MessagePojo> messagePojos=new ArrayList<MessagePojo>();

    ListView Listviewdata;

    public ShowDueDates() {
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


                String patt="^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
                String s1="\\d\\d\\d\\d-\\d\\d-\\d\\d";
                String s2="\\d\\d\\d\\d/\\d\\d/\\d\\d";
                String s3="\\d\\d/\\d\\d/\\d\\d\\d\\d";
                String s4="\\d\\d-\\d\\d-\\d\\d\\d\\d";


                patt=s1+"|"+s2+"|"+s3+"|"+s4;
                Pattern p1 = Pattern.compile(patt);
                Matcher m=p1.matcher(strbody);
                if (m.find()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    int flg=0;
                    try {
                        Date startDate = dateFormat.parse(m.group());
                        Date endDate=new Date();
                        long different = endDate.getTime() - startDate.getTime();
                        long secondsInMilli = 1000;
                        long minutesInMilli = secondsInMilli * 60;
                        long hoursInMilli = minutesInMilli * 60;
                        long daysInMilli = hoursInMilli * 24;
                        long elapsedDays = different / daysInMilli;
                        Log.i("TAG","dddddddd:"+elapsedDays);
                        if(elapsedDays<=0) {
                           String bdy= "" + strbody + "\n*Remaining Days:" + elapsedDays;
                            mp.setBody(bdy);
                            messagePojos.add(mp);
                        }
                        flg=1;
                    }
                    catch (Exception ex)
                    {

                    }
                    if(flg==0) {
                        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date startDate = dateFormat.parse(m.group());
                            Date endDate = new Date();
                            long different = endDate.getTime() - startDate.getTime();
                            long secondsInMilli = 1000;
                            long minutesInMilli = secondsInMilli * 60;
                            long hoursInMilli = minutesInMilli * 60;
                            long daysInMilli = hoursInMilli * 24;
                            long elapsedDays = different / daysInMilli;
                            Log.i("TAG", "dddddddd:" + elapsedDays);
                            if(elapsedDays<=0)
                            {
                                String bdy= "" + strbody + "\n*Remaining Days:" + elapsedDays;
                                mp.setBody(bdy);

                                messagePojos.add(mp);
                            }

                        } catch (Exception ex) {
                            Toast.makeText(view.getContext(), "err:" + ex, Toast.LENGTH_LONG).show();
                        }
                    }
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

}
