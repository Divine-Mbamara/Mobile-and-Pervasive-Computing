package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.model.Weather;
import com.example.weatherapp.view.RecycleViewAdapter;
import com.example.weatherapp.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    // Declare
    RecyclerView recyclerView;
    TextView input;
    Button button;
    RecycleViewAdapter recycleViewAdapter;
    WeatherViewModel weatherViewModel;
    List<Weather> weatherList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Set Variables
        input = view.findViewById(R.id.searchInput);
        button = view.findViewById(R.id.searchBtn1);

        recyclerView = view.findViewById(R.id.recycleView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recycleViewAdapter = new RecycleViewAdapter(requireActivity(), weatherList);
        recyclerView.setAdapter(recycleViewAdapter);

        // Setup LiveData
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Get current ip Weather
        weatherViewModel.queryWeather("auto:ip");

        weatherViewModel.getResponse().observe(getViewLifecycleOwner(), getData -> {
            weatherList.clear();
            weatherList.add(getData);
            recycleViewAdapter.notifyDataSetChanged();
        });

        // Search Btn
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = input.getText().toString().trim();
                if(search.isEmpty()) {
                    Toast.makeText(getContext(), "Type Search !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                weatherViewModel.queryWeather(search);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}