package com.example.rockpaperscissorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Variables
    EditText userName;
    Button luckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Variable Registration
        userName = findViewById(R.id.userName);
        luckBtn = findViewById(R.id.luckBtn);

        luckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString().trim();

                //Ensure user enters name
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_LONG).show();
                } else {
                    //Start second activity with user's name intent
                    Intent intentObj = new Intent(MainActivity.this, SecondActivity.class);
                    intentObj.putExtra("user_name", name);
                    startActivity(intentObj);
                }
            }
        });

    }
}