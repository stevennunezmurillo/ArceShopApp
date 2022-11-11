package com.example.proyectomviles;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int tabsTotales;

    public LoginAdapter(FragmentManager fragmentM, Context context, int tabsTotales){
        super(fragmentM);
        this.context = context;
        this.tabsTotales = tabsTotales;
    }
    //Metodo encargado de devolver el fragment que se necesita
    @Override
    public int getCount() {
        return tabsTotales;
    }

    public Fragment getItem(int posicion){
        switch(posicion){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegistroFragment();
            default:
                return null;
        }
    }
}
