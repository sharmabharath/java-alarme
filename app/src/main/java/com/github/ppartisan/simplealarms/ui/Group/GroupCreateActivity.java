package com.github.ppartisan.simplealarms.ui.Group;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ppartisan.simplealarms.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


    public class GroupCreateActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private EditText groupname;
    private EditText groupdesc;
    private FloatingActionButton okay;
    private ActionBar actionBar;//newly added

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        //group
        groupname = findViewById(R.id.groupname);
        groupdesc = findViewById(R.id.groupdesc);
        okay = findViewById(R.id.okay);


        //newly added
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Create Group");


        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        //newly added



        /*handle click event*/
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreatingGroup();
            }
        });

        //group

        FloatingActionButton floatingActionButton = findViewById(R.id.addMembers);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupCreateActivity.this, showUsers.class);
                startActivity(intent);

            }
        });
    }

        private void checkUser() {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null)
            {
                actionBar.setSubtitle(user.getDisplayName());
            }
        }

        private void startCreatingGroup() {
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Creating Group");

        String groupName = groupname.getText().toString().trim();
        String groupDescription = groupdesc.getText().toString().trim();

        if(TextUtils.isEmpty(groupName)){
            Toast.makeText(this,"Please enter group name!", Toast.LENGTH_SHORT).show();
            return;  //dont proceed further
        }

        progressDialog.show();

        //groupID, timecreated etc
        String g_timestamp = ""+System.currentTimeMillis();

        createGroup(
                ""+g_timestamp,
                ""+groupName,
                ""+groupDescription
        );

    }

    private void createGroup(String g_timestamp, String groupName, String groupDescription){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("groupId", ""+g_timestamp);
        hashMap.put("groupName",""+groupName);
        hashMap.put("groupDescription",""+groupDescription);
        hashMap.put("timestamp",""+g_timestamp);
        hashMap.put("createdBy",""+firebaseAuth.getUid());

        //create group

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(g_timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //created Successfully

                      /*  progressDialog.dismiss();
                        Toast.makeText(GroupCreateActivity.this, "Group Created Successfull!!!",Toast.LENGTH_SHORT).show();*/

                        //add member list and participants
                        HashMap<String, String> hashMap1 = new HashMap<>();
                        hashMap1.put("uid",firebaseAuth.getUid());
                        hashMap1.put("role","creator");
                        hashMap1.put("timestamp", g_timestamp);

                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Groups");
                        ref1.child(g_timestamp).child("Participants").child(firebaseAuth.getUid())
                                .setValue(hashMap1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //participant added
                                        progressDialog.dismiss();
                                        Toast.makeText(GroupCreateActivity.this,"Group created...",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //participant not added
                                        progressDialog.dismiss();
                                        Toast.makeText(GroupCreateActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed to create
                        progressDialog.dismiss();
                        Toast.makeText(GroupCreateActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //create group title and description
}


