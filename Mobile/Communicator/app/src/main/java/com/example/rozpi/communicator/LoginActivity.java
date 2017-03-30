package com.example.rozpi.communicator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    public final static String NICK = "com.example.rozpi.NICK";



    EditText etNick;
    Intent loginIntent;
    Context context;
    SharedPreferences sharedPreferences;
    Socket socket;
    String nick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etNick  = (EditText)findViewById(R.id.etNick);
        context = LoginActivity.this.getApplicationContext();
        sharedPreferences = context.getSharedPreferences("Nick",Context.MODE_PRIVATE);
        String nick = sharedPreferences.getString("Nick", "");
        Log.w("Preferences", nick);
        etNick.setText(nick);

        new SocketConnect().execute();




    }


    public void onLogin(View v) {
        if(SocketHandler.getSocket()!=null)
        {
            nick = etNick.getText().toString();

            if (nick.isEmpty()) {
                Toast.makeText(LoginActivity.this, R.string.no_nick, Toast.LENGTH_SHORT).show();
            } else {
                if (nick.contains(" ")) {
                    Toast.makeText(LoginActivity.this, R.string.nick_space, Toast.LENGTH_SHORT).show();
                } else {
                    loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.putExtra(NICK, nick);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Nick", nick);
                    editor.apply();
                    Log.w("SharedPref", nick);

                    socket = SocketHandler.getSocket();
                    new ServerConnect(false, nick, socket).execute();
                    startActivity(loginIntent);
                }
            }
        } else {
            Toast.makeText(LoginActivity.this, "No Connection To a Server", Toast.LENGTH_SHORT).show();
        }
    }



}


