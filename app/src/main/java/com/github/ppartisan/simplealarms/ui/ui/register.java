package com.github.ppartisan.simplealarms.ui.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ppartisan.simplealarms.R;

public class register extends AppCompatActivity {

    TextInputEditText textEmail, textPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textEmail = (TextInputEditText)findViewById(R.id.email_ed_register);
        textPassword = (TextInputEditText)findViewById(R.id.password_ed_register);
        progressBar = (ProgressBar)findViewById(R.id.progressBarRegister);
    }

    public void RegisterUser(View v)
    {

    }
}