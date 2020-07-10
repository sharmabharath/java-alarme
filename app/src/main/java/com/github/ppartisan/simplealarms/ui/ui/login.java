package com.github.ppartisan.simplealarms.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ppartisan.simplealarms.R;

public class login extends AppCompatActivity {

    TextInputEditText textEmail, textPassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textEmail = (TextInputEditText)findViewById(R.id.email_ed_login);
        textPassword = (TextInputEditText)findViewById(R.id.password_ed_login);
        progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);
    }

    public void LoginUser(View v)
    {

    }

    public void gotoRegister(View v)
    {
        Intent i = new Intent(login.this, register.class);
        startActivity(i);
    }

    public void forgotPassword(View v)
    {

    }
}