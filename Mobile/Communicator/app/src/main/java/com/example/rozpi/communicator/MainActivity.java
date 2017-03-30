package com.example.rozpi.communicator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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

    SharedPreferences sharedPreferences;

    NotificationManager manager;

    Socket socket;

    DateFormat dateFormat;


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

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        dateFormat = new SimpleDateFormat("HH:mm:ss");

        Intent loginIntent = getIntent();
        name = loginIntent.getStringExtra(LoginActivity.NICK);

        SocketHandler.setNick(name);
        sharedPreferences = getApplicationContext().getSharedPreferences("Nick",Context.MODE_PRIVATE);
        name = sharedPreferences.getString("Nick", "");
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
        BackgroundHandler.activityResumed();
        messageView.setAdapter(messageAdapter);
        manager.cancelAll();

    }

    @Override
    public void onPause() {
        super.onPause();
        BackgroundHandler.activityPaused();

    }

    private void onReceiveMessage(Intent intent) {
        String date = dateFormat.format(Calendar.getInstance().getTime());
        message = intent.getStringExtra("message");
        messageFromIntent = new String[2];
        messageFromIntent = message.split(" ", 2);
        Message tempMessage;
        tempMessage = new Message("Nick", message,date);
        messageList.add(tempMessage);
        messageAdapter.notifyDataSetChanged();
        scrollMyListViewToBottom();
        sendNotif(message);
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

    public void sendNotif(String nameMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notif_icon)
                        .setContentTitle("Czyjś Nick")
                        .setContentText(nameMessage);
        Intent notificationIntent = new Intent(this, EmptyActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        // Add as notification
        if(!BackgroundHandler.isActivityVisible())
            manager.notify(0, mBuilder.build());

    }


}
