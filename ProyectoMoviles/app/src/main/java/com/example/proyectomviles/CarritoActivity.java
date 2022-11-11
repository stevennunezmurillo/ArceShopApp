package com.example.proyectomviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CarritoActivity extends AppCompatActivity {

    private BottomNavigationView navBarBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);


        //Funcionalidad de navbar
        navBarBoton = findViewById(R.id.nav_viewBotones);
        navBarBoton.setSelectedItemId(R.id.navigation_carrito);
        navBarBoton.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_tienda:
                        startActivity(new Intent(getApplicationContext(),TiendaActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_usuario:
                        startActivity(new Intent(getApplicationContext(),UsuarioActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_carrito:
                        return true;
                }
                return false;
            }
        });
    }
}