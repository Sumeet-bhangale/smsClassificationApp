package com.example.smspoultry.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smspoultry.Fragments.ShowFavourite;
import com.example.smspoultry.MainActivity;
import com.example.smspoultry.Pojo.Fav;
import com.example.smspoultry.Pojo.MessagePojo;
import com.example.smspoultry.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MessageAdaptor extends BaseAdapter {

    private Context mContext;
    private ArrayList<MessagePojo> messagePojos = new ArrayList<MessagePojo>();
    String mob= "";
  int flg=0;
    String id="";

    public MessageAdaptor(Context context, ArrayList<MessagePojo> messagePojos)
    {
        this.mContext = context;
        this.messagePojos=messagePojos;

    }
    public MessageAdaptor(Context context, ArrayList<MessagePojo> messagePojos,int flg)
    {
        this.mContext = context;
        this.messagePojos=messagePojos;
        this.flg=flg;
    }

    @Override
    public int getCount() {
        return messagePojos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder holder;
        LayoutInflater layoutInflater;

        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_all_sms, null);
            holder = new viewHolder();
            holder.tvmob = (TextView) convertView.findViewById(R.id.tvmob);
            holder.txtbody = (MultiAutoCompleteTextView) convertView.findViewById(R.id.tvsms);
            holder.tvdate = (TextView) convertView.findViewById(R.id.tvdate);
            holder.imgfav = (ImageView) convertView.findViewById(R.id.imgfav);


            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        final MessagePojo mp=messagePojos.get(position);

        holder.tvmob.setText(mp.getContact());
        if(flg==1)
        {
            if(mp.getBody().toLowerCase().contains("debit"))
            {
                holder.txtbody.setTextColor(Color.RED);
            }
            else
            {
                holder.txtbody.setTextColor(mContext.getResources().getColor(R.color.colorgreen));
            }
        }
        holder.txtbody.setText(mp.getBody());
        holder.tvdate.setText(mp.getDate());
         id="";
        try
        {
            SharedPreferences prefs = mContext.getSharedPreferences("sms", MODE_PRIVATE);
            id = prefs.getString(mp.getId(), "");
            //Fav vi = gson.fromJson(json1, Fav.class);
        }
        catch (Exception ex)
        {

        }
        if (mp.getId().equalsIgnoreCase(id)) {
           // messagePojos.add(mp);
            holder.imgfav.setBackground(mContext.getDrawable(R.drawable.ic_baseline_star_24));
        }


        holder.imgfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equalsIgnoreCase("")) {
                    SharedPreferences.Editor prefsEditor = mContext.getSharedPreferences("sms", MODE_PRIVATE).edit();

                    prefsEditor.putString(mp.getId(), mp.getId());
                    prefsEditor.commit();

                    Toast.makeText(mContext, "added in favourite", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences.Editor prefsEditor = mContext.getSharedPreferences("sms", MODE_PRIVATE).edit();

                    prefsEditor.putString(mp.getId(), "");
                    prefsEditor.commit();

                    Toast.makeText(mContext, "Remove from favourite", Toast.LENGTH_SHORT).show();
                }

                Intent intent=new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return convertView;

    }
    public class viewHolder {
        TextView tvmob,tvdate;
        MultiAutoCompleteTextView txtbody;
        ImageView imgfav;
    }

}
