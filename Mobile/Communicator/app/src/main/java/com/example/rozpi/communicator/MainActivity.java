package com.example.rozpi.communicator;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ListView messageView;
    EditText messageBox;

    String name;
    String longName;
    String message;
    String[] messageFromIntent;

    ArrayList<Message> messageList = new ArrayList<>();
    MessageAdapter messageAdapter;

    Socket socket;

    DateFormat dateFormat;

    private static final String TAG = "MainActivity";
    Intent recive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageAdapter = new MessageAdapter(this, messageList);
        messageBox = (EditText)findViewById(R.id.messageBox);
        messageView = (ListView) findViewById(R.id.messageList);
        messageView.setAdapter(messageAdapter);
        socket = SocketHandler.getSocket();
        recive = new Intent(getApplicationContext(),ServerReceive.class);

        dateFormat = new SimpleDateFormat("HH:mm:ss");

        Intent loginIntent = getIntent();
        name = loginIntent.getStringExtra(LoginActivity.NICK);

        SocketHandler.setNick(name);

        if(name.length()>7) {
            longName = name.substring(0, 3)+"..."+name.charAt(name.length()-1);
        }
        registerForContextMenu(messageView);

        messageBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND) {
                    onSend(v);
                    return true;
                }else
                return false;
            }
        });

    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(messageAdapter.getItem(info.position).getSender());
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
        new ServerConnect(true, message, socket).execute();
        messageBox.setText("");

        getApplicationContext().startService(recive);
    }



    @Override
    public void onResume() {
        super.onResume();
        startService(recive);
        registerReceiver(broadcastReceiver, new IntentFilter(ServerReceive.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
       // unregisterReceiver(broadcastReceiver);
        //stopService(recive);
    }

    private void onReceiveMessage(Intent intent) {
        String date = dateFormat.format(Calendar.getInstance().getTime());
        message = intent.getStringExtra("message");
        messageFromIntent = new String[2];
        messageFromIntent = message.split(" ", 2);
        Message tempMessage;
        if(!(messageFromIntent[0].equals("LOGIN"))) {
            tempMessage = new Message(messageFromIntent[0], messageFromIntent[1], date);
        } else {
            tempMessage = new Message("Server", messageFromIntent[1], date);
        }
        messageList.add(tempMessage);
        messageAdapter.notifyDataSetChanged();
        scrollMyListViewToBottom();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceiveMessage(intent);
        }
    };

    private void scrollMyListViewToBottom() {
        messageView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                messageView.setSelection(messageAdapter.getCount() - 1);
            }
        });
    }


}
