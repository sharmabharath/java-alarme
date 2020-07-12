package com.github.ppartisan.simplealarms.ui.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ppartisan.simplealarms.R;
import com.github.ppartisan.simplealarms.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    TextInputEditText textEmail, textPassword;
    ProgressBar progressBar;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            Intent i = new Intent(login.this, MainActivity.class);
            startActivity(i);
        }

        else {

            setContentView(R.layout.activity_login);

            textEmail = (TextInputEditText)findViewById(R.id.email_ed_login);
            textPassword = (TextInputEditText)findViewById(R.id.password_ed_login);
            progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);
            reference= FirebaseDatabase.getInstance().getReference().child("Users");
        }
    }

    public void LoginUser(View v)
    {
        progressBar.setVisibility(View.VISIBLE);

        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        if(!email.equals("") && !password.equals("") )
        {
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Welcome to AlarmE", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(login.this, MainActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "WRONG EMAIL OR PASSWORD :(", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
        }
    }

    public void gotoRegister(View v)
    {
        Intent i = new Intent(login.this, register.class);
        startActivity(i);
    }

    public void forgotPassword(View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(login.this);

        LinearLayout container = new LinearLayout(login.this);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        ip.setMargins(50,0,0,100);
        final EditText input = new EditText(login.this);
        input.setLayoutParams(ip);
        input.setGravity(Gravity.TOP|Gravity.START);
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.setLines(1);

        container.addView(input,ip);
        alert.setMessage("Enter your registered email address");
        alert.setTitle("FORGOT PASSWORD");
        alert.setView(container);

        alert.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String entered_email = input.getText().toString();
                auth.sendPasswordResetEmail(entered_email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    dialogInterface.dismiss();
                                    Toast.makeText(getApplicationContext(),"Email sent please check your email",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}