package com.github.ppartisan.simplealarms.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ppartisan.simplealarms.R;
import com.github.ppartisan.simplealarms.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    TextInputEditText textEmail, textPassword, textName;
    ProgressBar progressBar;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textEmail = (TextInputEditText)findViewById(R.id.email_ed_register);
        textPassword = (TextInputEditText)findViewById(R.id.password_ed_register);
        textName = (TextInputEditText)findViewById(R.id.name_ed_register);
        progressBar = (ProgressBar)findViewById(R.id.progressBarRegister);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void RegisterUser(View v)
    {
        progressBar.setVisibility(View.VISIBLE);
        final String email = textEmail.getText().toString();
        final String password = textPassword.getText().toString();
        final String name = textName.getText().toString();

        if(!email.equals("") && !password.equals("") && password.length()>6)
        {
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                 //insert value in database
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                User u = new User();
                                u.setName(name);
                                u.setEmail(email);

                                reference.child(firebaseUser.getUid()).setValue(u)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(getApplicationContext(),"Registered Successfully!",Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    finish();
                                                    Intent i = new Intent(register.this,login.class);
                                                    startActivity(i);
                                                }

                                                else
                                                {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(),"User could not be created",Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                            }

                        }
                    });
        }

    }

    public void gotoLogin(View v)
    {
        Intent i = new Intent(register.this, login.class);
        startActivity(i);
    }
}






















