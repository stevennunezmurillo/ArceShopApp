package com.example.proyectomviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UsuarioActivity extends AppCompatActivity {

    private BottomNavigationView navBarBoton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        navBarBoton = findViewById(R.id.nav_viewBotones);
        navBarBoton.setSelectedItemId(R.id.navigation_usuario);
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
                        return true;
                    case R.id.navigation_carrito:
                        startActivity(new Intent(getApplicationContext(),CarritoActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}