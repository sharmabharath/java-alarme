package com.github.ppartisan.simplealarms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ppartisan.simplealarms.R;
import com.github.ppartisan.simplealarms.ui.Group.GroupCreateActivity;
import com.github.ppartisan.simplealarms.ui.Login.login;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private Button button;


FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button = (Button) findViewById(R.id.create_group);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGroupCreateActivity();
            }
        });
    }


    public void openGroupCreateActivity(){
        Intent intent = new Intent(this, GroupCreateActivity.class);
        startActivity(intent);
    }

    /*inflate logout menu buttons*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating enu
        getMenuInflater().inflate(R.menu.logout_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*handle menu items*/
    public boolean onOptionsItemSelected(MenuItem item){
        //get id
        int id = item.getItemId();
        if (id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, login.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
