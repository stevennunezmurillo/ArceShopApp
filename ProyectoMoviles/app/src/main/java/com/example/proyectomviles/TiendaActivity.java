package com.example.proyectomviles;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectomviles.ui.paginaTienda.Product;
import com.example.proyectomviles.ui.paginaTienda.Products;
import com.example.proyectomviles.ui.paginaTienda.TiendaAdapter;
import com.example.proyectomviles.utilidades.InternetListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TiendaActivity extends AppCompatActivity {
    private final String URLEXAMPLE = "https://dummyjson.com/products";
    private ArrayList<Product> list;
    private TiendaAdapter adapter;
    private EditText buscador;
    private BottomNavigationView navBarBoton;
    InternetListener internetListener = new InternetListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tienda_activity);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        StringRequest myRequest = new StringRequest(Request.Method.GET,
                URLEXAMPLE,
                response -> {
                    try{
                        Gson gson = new Gson();
                        Products productsList = gson.fromJson(response,Products.class);
                        list = productsList.products.stream().collect(Collectors.toCollection(ArrayList::new));
                        adapter = new TiendaAdapter(this,list);
                        LinearLayoutManager manager = new LinearLayoutManager(this);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(this,
                        volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);
        buscador = findViewById(R.id.buscador);
        //La parte importante de esto es el afterTextchanged, el cual nos permite filtrar por palabras clave
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        //Funcionalidad de navbar
        navBarBoton = findViewById(R.id.nav_viewBotones);
        navBarBoton.setSelectedItemId(R.id.navigation_tienda);
        navBarBoton.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_tienda:
                        return true;
                    case R.id.navigation_usuario:
                        startActivity(new Intent(getApplicationContext(),UsuarioActivity.class));
                        overridePendingTransition(0,0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        ArrayList<Product> filteredlist = new ArrayList<Product>();
        for (Product item : list) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }
    @Override
    protected void onStart(){
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop(){
        unregisterReceiver(internetListener);
        super.onStop();
        finish();
    }

}