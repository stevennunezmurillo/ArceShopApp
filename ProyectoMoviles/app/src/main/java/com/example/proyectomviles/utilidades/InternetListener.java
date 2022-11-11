package com.example.proyectomviles.utilidades;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.example.proyectomviles.InicioActivity;
import com.example.proyectomviles.LoginActivity;
import com.example.proyectomviles.R;

public class InternetListener extends BroadcastReceiver {
    //Método que recibe si hay o no internet y en caso de no haber muestra un dialogo al usuario de que no hay internet
    @Override
    public void onReceive(Context context, Intent intent){
        if(!ConexionInternet.hayConexion(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout = LayoutInflater.from(context).inflate(R.layout.conexion_internet_dialog,null);
            builder.setView(layout);
            AppCompatButton botonReIntentar = layout.findViewById(R.id.botonRetry);
            AppCompatButton botonVolverMenu = layout.findViewById(R.id.botonDeslogin);
            AlertDialog alert = builder.create();
            alert.setCancelable(false);
            alert.getWindow().setGravity(Gravity.CENTER);
            //Este nos permite que el usuario pueda recargar la página en caso de que se le vaya el internet y así no pierda su login
            botonReIntentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.dismiss();
                    onReceive(context, intent);
                }
            });
            //Con este botón volveríamos al menu de inicio, saliendonos de la apliación
            botonVolverMenu.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, InicioActivity.class);
                    context.startActivity(intent);
                }
            });
            alert.show();

        }
    }
}
