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

public class LoginActivity extends AppCompatActivity {

    public final static String NICK = "com.example.rozpi.NICK";

    EditText etNick;
    Intent loginIntent;
    Context context;
    SharedPreferences sharedPreferences;

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

    }



    public void onLogin(View v) {
        if (etNick.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this,R.string.no_nick,Toast.LENGTH_SHORT).show();
        }else {
            loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            loginIntent.putExtra(NICK, etNick.getText().toString());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Nick", etNick.getText().toString());
            editor.apply();
            Log.w("SharedPref", etNick.getText().toString());
            startActivity(loginIntent);
        }
    }




}
