package com.example.rozpi.communicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView messageView;
    EditText messageBox;

    String name;
    String message;

    ArrayList<Message> messageList = new ArrayList<>();
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageAdapter = new MessageAdapter(this, messageList);
        messageBox = (EditText)findViewById(R.id.messageBox);
        messageView = (ListView) findViewById(R.id.messageList);
        messageView.setAdapter(messageAdapter);

        Intent loginIntent = getIntent();
        name = loginIntent.getStringExtra(LoginActivity.NICK);

    }

    public void onSend(View v) {
        message = messageBox.getText().toString();
        Message tempMessage = new Message(name, message);
        messageAdapter.addAll(tempMessage);
        messageBox.setText("");
    }
}
