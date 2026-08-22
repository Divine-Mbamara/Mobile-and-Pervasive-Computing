package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.model.Weather;
import com.example.weatherapp.view.RecycleViewAdapter;
import com.example.weatherapp.viewmodel.WeatherViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    // Declare
    RecyclerView recyclerView;
    RecycleViewAdapter recycleViewAdapter;
    WeatherViewModel weatherViewModel;
    List<Weather> weatherList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Set recyclerView
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Set Adapter
        recycleViewAdapter = new RecycleViewAdapter(requireActivity(), weatherList);
        recyclerView.setAdapter(recycleViewAdapter);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Setup LiveData
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Check data exist
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    List<String> favorites = (List<String>) documentSnapshot.get("Favorites");

                    if (favorites != null) {
                        for (String item : favorites) {
                            weatherViewModel.queryWeather(item);
                        }
                    }
                }
            }
        });

        weatherViewModel.getResponse().observe(getViewLifecycleOwner(), getData -> {
            weatherList.add(getData);
            recycleViewAdapter.notifyDataSetChanged();
        });

        return view;
    }
}