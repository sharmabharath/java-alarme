package com.github.ppartisan.simplealarms.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.github.ppartisan.simplealarms.R;

public class MainActivity extends AppCompatActivity {
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.create_group);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateGroup();
            }
        });
    }

    public void openCreateGroup(){
        Intent intent = new Intent(this, CreateGroup.class);
        startActivity(intent);
    }
}
