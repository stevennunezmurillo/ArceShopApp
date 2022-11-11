package com.example.proyectomviles.utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class ConexionInternet {

    //Método que detecta cuando el dispositivo tiene internet o no, utilizando
    public static boolean hayConexion(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Utilizamos este if con el fin de poder manejar distintas versiones.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null){
                return false;
            }
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            boolean tieneInternet = networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) );
            return tieneInternet;
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean tieneInternet = networkInfo != null && networkInfo.isConnected();
            return tieneInternet;
        }
    }
}
