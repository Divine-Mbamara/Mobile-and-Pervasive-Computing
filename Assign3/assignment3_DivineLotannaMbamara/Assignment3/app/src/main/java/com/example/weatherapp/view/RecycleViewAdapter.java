package com.example.weatherapp.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.model.Weather;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewHolder> {
    // Declare
    Context context;
    List<Weather> weatherList;
    List<String> myWeathers;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Construction
    public RecycleViewAdapter(Context context, List<Weather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Weather weather = weatherList.get(position);

        // Set Variable
        holder.favoriteFunc.setText("ADD");
        // Check data exist
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    List<String> favorites = (List<String>) documentSnapshot.get("Favorites");

                    if (favorites != null) {
                        for (String item : favorites) {
                            if(Objects.equals(item, weather.getName())) {
                                holder.favoriteFunc.setText("REMOVE");
                                break;
                            }
                        }
                    }
                }
            }
        });

        Glide.with(holder.imageView).load("https:" + weather.getIcon()).into(holder.imageView);
        holder.name.setText(weather.getName());
        holder.cond.setText(weather.getCondition());
        holder.deg.setText(weather.getTempC() + "°C");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SecondActivity.class);
            intent.putExtra("q", weather.getName());
            context.startActivity(intent);
        });

        // Add/Remove Btn
        holder.favoriteFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check events validation
                if(holder.favoriteFunc.getText().toString().equals("ADD")) {
                    // Add event
                    db.collection("Users").document(mAuth.getCurrentUser().getUid())
                        // Creates the document if missing and appends to the array
                        .set(
                                Collections.singletonMap("Favorites", FieldValue.arrayUnion(weather.getName())),
                                SetOptions.merge()
                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Success Add To Favorites", Toast.LENGTH_SHORT).show();

                                holder.favoriteFunc.setText("REMOVE");
                            }
                        });
                } else if (holder.favoriteFunc.getText().toString().equals("REMOVE")){
                    // Remove event
                    db.collection("Users").document(mAuth.getCurrentUser().getUid())
                        .update("Favorites", FieldValue.arrayRemove(weather.getName())).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Success Remove Of Favorites", Toast.LENGTH_SHORT).show();

                                holder.favoriteFunc.setText("ADD");
                            }
                        });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
