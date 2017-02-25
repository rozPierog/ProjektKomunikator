package com.example.rozpi.communicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public final static String NICK = "com.example.rozpi.NICK";

    EditText etNick;
    Intent loginIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etNick  = (EditText)findViewById(R.id.etNick);
    }

    public void onLogin(View v) {
        loginIntent = new Intent(this, MainActivity.class);
        loginIntent.putExtra(NICK,etNick.getText().toString());
        startActivity(loginIntent);
    }


}
