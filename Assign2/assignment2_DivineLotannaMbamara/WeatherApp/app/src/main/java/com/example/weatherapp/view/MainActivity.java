package com.example.weatherapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    WeatherViewModel viewModel;

    String[] locations = {"Toronto", "Montreal", "Vancouver"};
    ArrayList<Weather> weathers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup LiveData
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        for (String location: locations) {
            viewModel.queryWeather(location);
        }

        viewModel.getResponse().observe(this, getData -> {
            weathers.add(getData);

            // Loop weathers to show to View
            for (Weather weather: weathers) {
                if (Objects.equals(weather.getName(), "Toronto")) {
                    Glide.with(this).load("https:" + weather.getIcon()).into(binding.iconToronto);
                    binding.condToronto.setText(weather.getCondition());
                    binding.degToronto.setText(weather.getTempC() + "°C");
                } else if (Objects.equals(weather.getName(), "Montreal")) {
                    Glide.with(this).load("https:" + weather.getIcon()).into(binding.iconMontreal);
                    binding.condMontreal.setText(weather.getCondition());
                    binding.degMontreal.setText(weather.getTempC() + "°C");
                } else if (Objects.equals(weather.getName(), "Vancouver")) {
                    Glide.with(this).load("https:" + weather.getIcon()).into(binding.iconVancouver);
                    binding.condVancouver.setText(weather.getCondition());
                    binding.degVancouver.setText(weather.getTempC() + "°C");
                }
            }
        });

        binding.torontoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("q", "Toronto");
                startActivity(intent);
            }
        });

        binding.montrealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("q", "Montreal");
                startActivity(intent);
            }
        });

        binding.vancouverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("q", "Vancouver");
                startActivity(intent);
            }
        });
    }
}