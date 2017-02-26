package com.example.rozpi.communicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


class MessageAdapter extends ArrayAdapter<Message> {

    MessageAdapter(Context context, ArrayList<Message> mess) {
        super(context, 0, mess);
    }
    private Message message;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        message = getItem(position);
        String longName;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }
        Button senderName = (Button) convertView.findViewById(R.id.senderName);
        TextView tvMessages = (TextView) convertView.findViewById(R.id.message);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        if(message.sender.length()>7) {
            longName = message.sender.substring(0, 3)+"..."+message.sender.charAt(message.sender.length()-1);
            senderName.setText(longName);
        }else senderName.setText(message.sender);

        tvMessages.setText(message.message);
        tvTime.setText(message.timestamp);



        senderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), message.sender,Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
