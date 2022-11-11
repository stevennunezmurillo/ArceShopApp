package com.example.proyectomviles;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment implements View.OnClickListener{
    EditText correo;
    EditText contrasena;
    Button btnLogin;
    RegistroUsuarios registro;
    Button botonTienda;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup raiz = (ViewGroup) inflater.inflate(R.layout.login_fragment,container, false);
        correo = raiz.findViewById(R.id.correoElectronico);
        contrasena = raiz.findViewById(R.id.contrasena);
        registro = new RegistroUsuarios(this.getContext());
        correo.animate().translationX(0).alpha(1).setDuration(200).setStartDelay(300).start();
        contrasena.animate().translationX(0).alpha(1).setDuration(200).setStartDelay(300).start();
        btnLogin = (Button) raiz.findViewById(R.id.botonLogin);
        btnLogin.setOnClickListener(this);
        //Boton para ir a tienda de manera temporal

        return raiz;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botonLogin:
                if(!isConnected()){
                    Toast.makeText(getActivity(), "No hay conexión a internet", Toast.LENGTH_LONG).show();
                }else{
                String corr = correo.getText().toString();
                String cont = contrasena.getText().toString();
                if(corr.equals("") || cont.equals("")){
                    Toast.makeText(getActivity(), "ERROR: Revise que se ingresaran todos los datos", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        Usuario usuario = registro.getUsuario(corr, cont);
                        if(usuario != null) {
                            if(usuario.getPrimerLogin() == 1){
                                Intent intent = new Intent(getActivity(), PasswordActivity.class);
                                intent.putExtra("correo", usuario.getCorreo());
                                intent.putExtra("contrasena", cont);
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(getActivity(), TiendaActivity.class);
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "ERROR: Usuario y/o contraseña inválidos", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Ocurrió un error, intente de nuevo", Toast.LENGTH_LONG).show();
                    }
                }
                }
                break;

        }
    }
    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}


