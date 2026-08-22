package com.example.weatherapp.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

public class RecycleViewHolder extends RecyclerView.ViewHolder {
    // Declare
    ImageView imageView;
    TextView cond, name, deg, favoriteFunc;

    public RecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        // Match Variables with View
        imageView = itemView.findViewById(R.id.icon);
        cond = itemView.findViewById(R.id.cond);
        name = itemView.findViewById(R.id.name);
        deg = itemView.findViewById(R.id.deg);
        favoriteFunc = itemView.findViewById(R.id.favoriteFun);
    }
}
