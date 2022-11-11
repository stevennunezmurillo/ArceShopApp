package com.example.proyectomviles;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;


public class LoginActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        tab = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_page);
        tab.addTab(tab.newTab().setText("Login"));
        tab.addTab(tab.newTab().setText("Registrarse"));
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adaptador = new LoginAdapter(getSupportFragmentManager(), this, tab.getTabCount());
        viewPager.setAdapter(adaptador);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab1) {
                viewPager.setCurrentItem(tab1.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    }

        public Double getLatitud(){
        return  Double.parseDouble(getIntent().getStringExtra("latitud"));
        }
        public Double getLongitud(){
            return Double.parseDouble(getIntent().getStringExtra("longitud"));
        }


}