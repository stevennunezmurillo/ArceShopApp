package com.example.proyectomviles;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proyectomviles.utilidades.InternetListener;



public class PasswordActivity extends AppCompatActivity {

    EditText contrasena_confirmacion;
    EditText contrasena_nueva;
    Button btnConfirm;
    RegistroUsuarios registro;
    String correo, contrasena;
    InternetListener internetListener = new InternetListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        contrasena_nueva = (EditText) findViewById(R.id.nuevaContrasena);
        contrasena_confirmacion = (EditText) findViewById(R.id.confirmarContrasena);

        correo = getIntent().getStringExtra("correo");
        contrasena = getIntent().getStringExtra("contrasena");
        registro = new RegistroUsuarios(this);

        contrasena_nueva.animate().translationX(0).alpha(1).setDuration(200).setStartDelay(300).start();
        contrasena_confirmacion.animate().translationX(0).alpha(1).setDuration(200).setStartDelay(300).start();

        btnConfirm = (Button) findViewById(R.id.botonConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(validarContrasena()) {

                    switch (v.getId()) {
                        case R.id.botonConfirm:

                            String contr_nuev = contrasena_nueva.getText().toString();
                            String contra_confirm = contrasena_confirmacion.getText().toString();

                            if (contra_confirm.equals("") || contr_nuev.equals("")) {
                                Toast.makeText(PasswordActivity.this, "ERROR: Rellene todos los espacios solicitados", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    Usuario usuario = registro.getUsuario(correo, contrasena);

                                    if (registro.editarContrasena(usuario, contra_confirm)) {
                                        Intent intent = new Intent(PasswordActivity.this, TiendaActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(PasswordActivity.this, "ERROR: Intente nuevamente", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(PasswordActivity.this, "ERROR: Intente nuevamente2", Toast.LENGTH_LONG).show();
                                }
                            }
                            break;
                    }
                }
            }
        });
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

    private boolean esContrasenaValida(String contra_nueva, String confirmacion) {

        if(!(contra_nueva.equals(confirmacion)) || confirmacion.length() < 8){
            contrasena_nueva.setError("Las contaseñas no coinciden o es menor a 8 carácteres");
            contrasena_confirmacion.setError("Las contaseñas no coinciden o es menor a 8 carácteres");
            return false;
        } else {
            contrasena_nueva.setError(null);
            contrasena_confirmacion.setError(null);
        }

        return true;
    }

    private boolean validarContrasena() {
        String contra_nueva = contrasena_nueva.getText().toString();
        String confirmacion = contrasena_confirmacion.getText().toString();

        if (esContrasenaValida(contra_nueva, confirmacion)) {
            return true;
        }else{
            return false;
        }
    }

}



