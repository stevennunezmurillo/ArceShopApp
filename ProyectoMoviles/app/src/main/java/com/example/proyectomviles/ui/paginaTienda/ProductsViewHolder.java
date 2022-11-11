package com.example.proyectomviles.ui.paginaTienda;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomviles.R;


public class ProductsViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView tittleView,subtittleView;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.icon);
        tittleView = itemView.findViewById(R.id.title);
        subtittleView = itemView.findViewById(R.id.subtitle);
    }
}
