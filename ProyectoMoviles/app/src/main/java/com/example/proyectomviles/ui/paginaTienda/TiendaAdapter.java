package com.example.proyectomviles.ui.paginaTienda;

import android.app.Activity;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomviles.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;


public class TiendaAdapter extends RecyclerView.Adapter<ProductsViewHolder> {

    private final Activity context;
    private ArrayList<Product> products;

    Handler mainHandler = new Handler();


    public TiendaAdapter(Activity context, ArrayList<Product> products) {
        this.context=context;
        this.products=products;

    }

    public void filterList(ArrayList<Product> filterlist) {
        products = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  ProductsViewHolder holder, int position) {
        holder.tittleView.setText(products.get(position).getTitle());
        Double price = products.get(position).getPrice();
        holder.subtittleView.setText("$" + price.toString());
        Picasso.with(context).load(products.get(position).thumbnail).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Crea las variables para los views
        private final TextView productTittle;
        private final TextView productSubTittle;
        private final ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las variables de los views
            productTittle = itemView.findViewById(R.id.title);
            productSubTittle = itemView.findViewById(R.id.subtitle);
            productImage = itemView.findViewById(R.id.imageView);
        }
    }


}
