package com.example.rockpaperscissorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    //Variables
    Button rockBtn, scissBtn, paperBtn, shareBtn, backBtn;
    TextView luckMsg, scoreView;
    Random random = new Random();
    int userScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        //Variable Registration
        rockBtn = findViewById(R.id.rockBtn);
        scissBtn = findViewById(R.id.scissBtn);
        paperBtn = findViewById(R.id.paperBtn);
        shareBtn = findViewById(R.id.shareBtn);
        backBtn = findViewById(R.id.backBtn);
        luckMsg = findViewById(R.id.luckMsg);
        scoreView = findViewById(R.id.scoreView);

        //Display Username from Intent obj
        Intent intentObj = getIntent();
        String usrNam = intentObj.getStringExtra("user_name");
        luckMsg.setText(usrNam + ", Good Luck!!");

        //RPS Buttons Functionality
        //Rock
        rockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user selection
                String userSelection = "Rock";

                //app selection
                int randNum = random.nextInt(3);
                String appSelection = "";
                if(randNum == 0){
                    appSelection = "Rock";
                }
                else if(randNum == 1){
                    appSelection = "Paper";
                }
                else{
                    appSelection = "Scissors";
                }

                //compare selection
                compareSelection(userSelection, appSelection);
            }
        });

        //Paper
        paperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user selection
                String userSelection = "Paper";

                //app selection
                int randNum = random.nextInt(3);
                String appSelection = "";
                if(randNum == 0){
                    appSelection = "Rock";
                }
                else if(randNum == 1){
                    appSelection = "Paper";
                }
                else{
                    appSelection = "Scissors";
                }

                //compare selection
                compareSelection(userSelection, appSelection);
            }
        });

        //Scissors
        scissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user selection
                String userSelection = "Scissors";

                //app selection
                int randNum = random.nextInt(3);
                String appSelection = "";
                if(randNum == 0){
                    appSelection = "Rock";
                }
                else if(randNum == 1){
                    appSelection = "Paper";
                }
                else{
                    appSelection = "Scissors";
                }

                //compare selection
                compareSelection(userSelection, appSelection);
            }
        });

        // Share Button Functionality
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Score Share functionality
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String sharedScore = usrNam + "'s score is " + scoreView.getText().toString();
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharedScore);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        // Back Button Functionality
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send user back to first activity
                Intent backIntent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

    //Functionality for comparing User and App selection
    private void compareSelection(String userSelection, String appSelection){
        String toastMsg = "You chose " + userSelection + ", App chose " + appSelection + ". ";

        if(userSelection.equals(appSelection)){
            toastMsg += "TIE!";
        }
        else if((userSelection.equals("Rock") && appSelection.equals("Scissors")) ||
                (userSelection.equals("Paper") && appSelection.equals("Rock")) ||
                (userSelection.equals("Scissors") && appSelection.equals("Paper"))
        ){
            userScore++;
            toastMsg += "YOU WIN!";
        }
        else{
            userScore = 0;
            toastMsg += "APP WINS! GAME OVER";

            //send user back to first activity
            Intent backIntent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(backIntent);
        }

        //Display Toast Message
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();

        //Display User Score
        scoreView.setText("" + userScore);


    }

}