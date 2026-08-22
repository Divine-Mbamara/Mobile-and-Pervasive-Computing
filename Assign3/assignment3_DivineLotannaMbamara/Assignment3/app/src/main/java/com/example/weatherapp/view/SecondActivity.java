package com.example.weatherapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivitySecondBinding;
import com.example.weatherapp.viewmodel.WeatherViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    // Declare
    ActivitySecondBinding binding;
    WeatherViewModel viewModel;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Data of First View
        Intent intObj = getIntent();
        String q = intObj.getStringExtra("q");

        // Setup ViewBinding
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Setup LiveData
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Get Weather
        viewModel.queryWeather(q);

        viewModel.getResponse().observe(this, getData -> {
            binding.favoriteBtnFun.setText("ADD");
            db.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        List<String> favorites = (List<String>) documentSnapshot.get("Favorites");

                        if (favorites != null) {
                            for (String item : favorites) {
                                if(Objects.equals(item, getData.getName())) {
                                    binding.favoriteBtnFun.setText("REMOVE");
                                    break;
                                }
                            }
                        }
                    }
                }
            });

            // Add values to Item
            binding.city.setText(getData.getName());
            binding.location.setText(getData.getCountry());
            binding.tempC.setText(getData.getTempC().toString() + "°C");
            binding.tempF.setText(getData.getTempF().toString() + "°F");
            Glide.with(this).load("https:" + getData.getIcon()).into(binding.img);
            binding.condition.setText(getData.getCondition());
            binding.feelLike.setText(getData.getFeelLikeC().toString() + "°C/" + getData.getFeelLikeF() + "°F");
            binding.windSpeed.setText(getData.getWindMph().toString() + "(m/h), " + getData.getWindKph().toString() + "(k/h)");
            binding.windDirect.setText(getData.getWindDegree().toString() + "° " + getData.getWindDirect());

            // Add/Remove Btn
            binding.favoriteBtnFun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check events validation
                    if(binding.favoriteBtnFun.getText().toString().equals("ADD")) {
                        // Add event
                        db.collection("Users").document(mAuth.getCurrentUser().getUid())
                            // Creates the document if missing and appends to the array
                            .set(
                                    Collections.singletonMap("Favorites", FieldValue.arrayUnion(getData.getName())),
                                    SetOptions.merge()
                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Success Add To Favorites", Toast.LENGTH_SHORT).show();

                                    binding.favoriteBtnFun.setText("REMOVE");
                                }
                            });
                    } else if (binding.favoriteBtnFun.getText().toString().equals("REMOVE")){
                        // Remove event
                        db.collection("Users").document(mAuth.getCurrentUser().getUid())
                            .update("Favorites", FieldValue.arrayRemove(getData.getName())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Success Remove Of Favorites", Toast.LENGTH_SHORT).show();

                                    binding.favoriteBtnFun.setText("ADD");
                                }
                            });
                    }
                }
            });
        });

        // Back Btn
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}