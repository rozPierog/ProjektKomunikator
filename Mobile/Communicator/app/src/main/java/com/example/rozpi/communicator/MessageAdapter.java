package com.example.rozpi.communicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rozpi on 24.02.2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, ArrayList<Message> mess) {
        super(context, 0, mess);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }
        TextView senderName = (TextView) convertView.findViewById(R.id.senderName);
        TextView tvMessages = (TextView) convertView.findViewById(R.id.message);

        senderName.setText(message.sender);
        tvMessages.setText(message.message);

        return convertView;
    }
}
