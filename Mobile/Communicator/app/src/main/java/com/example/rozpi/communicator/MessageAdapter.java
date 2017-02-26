package com.example.rozpi.communicator;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rozpi on 24.02.2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, ArrayList<Message> mess) {
        super(context, 0, mess);
    }
    Message message;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        message = getItem(position);
        String longName;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }
        Button senderName = (Button) convertView.findViewById(R.id.senderName);
        TextView tvMessages = (TextView) convertView.findViewById(R.id.message);
        if(message.sender.length()>7) {
            longName = message.sender.substring(0, 3)+"..."+message.sender.charAt(message.sender.length()-1);
            senderName.setText(longName);
        }else senderName.setText(message.sender);

        tvMessages.setText(message.message);



        senderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), message.sender,Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
