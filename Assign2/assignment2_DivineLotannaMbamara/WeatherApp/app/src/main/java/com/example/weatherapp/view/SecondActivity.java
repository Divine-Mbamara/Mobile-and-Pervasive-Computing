package com.example.weatherapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.databinding.ActivitySecondBinding;
import com.example.weatherapp.viewmodel.WeatherViewModel;

public class SecondActivity extends AppCompatActivity {

    // Declare
    ActivitySecondBinding binding;
    WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Data of First View
        Intent intObj = getIntent();
        String q = intObj.getStringExtra("q");

        // Setup ViewBinding
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup LiveData
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        viewModel.queryWeather(q);

        viewModel.getResponse().observe(this, getData -> {
            binding.city.setText(getData.getName());
            binding.location.setText(getData.getCountry());
            binding.tempC.setText(getData.getTempC().toString() + "°C");
            binding.tempF.setText(getData.getTempF().toString() + "°F");
            Glide.with(this).load("https:" + getData.getIcon()).into(binding.img);
            binding.condition.setText(getData.getCondition());
            binding.feelLike.setText(getData.getFeelLikeC().toString() + "°C/" + getData.getFeelLikeF() + "°F");
            binding.windSpeed.setText(getData.getWindMph().toString() + "(m/h), " + getData.getWindKph().toString() + "(k/h)");
            binding.windDirect.setText(getData.getWindDegree().toString() + "° " + getData.getWindDirect());
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}