package com.example.perez_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class User extends AppCompatActivity {

    public static String Username;

    TextView UserNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        UserNameText = (TextView) findViewById(R.id.usernamedisplay);
        UserNameText.setText("Welcome! : " + Username);
    }

    public void close(View view) {
        finish();
    }
}