package com.example.rozpi.communicator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        registerForContextMenu(messageView);
//        Button lpCopy = (Button)findViewById(R.id.longpressCopy);
//        Button lpDelete = (Button)findViewById(R.id.longpressDelete);
//        registerForContextMenu(lpCopy);
//        registerForContextMenu(lpDelete);

//        messageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                return false;
//            }
//        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, v.getId(), 0, R.string.copy);
        menu.add(Menu.NONE, v.getId(), 0, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getTitle().equals(getString(R.string.copy))){

            ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("", messageList.get(info.position).message);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(getApplicationContext(),R.string.copied,Toast.LENGTH_SHORT).show();
        } else if(item.getTitle().equals(getString(R.string.delete))) {
            messageList.remove(info.position);
            messageAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),R.string.deleted,Toast.LENGTH_SHORT).show();
        } else return false;
        return true;
    }

    public void onSend(View v) {
        message = messageBox.getText().toString();
        Message tempMessage = new Message(name, message);
        messageList.add(tempMessage);
        messageAdapter.notifyDataSetChanged();
        messageBox.setText("");
    }
}
