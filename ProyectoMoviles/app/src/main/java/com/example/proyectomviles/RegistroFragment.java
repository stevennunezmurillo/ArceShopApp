package com.example.proyectomviles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import androidx.fragment.app.Fragment;
import java.util.Locale;
import java.util.regex.Pattern;


public class RegistroFragment extends Fragment implements View.OnClickListener {

    private EditText identificacion;
    private EditText correoElectronico;
    private EditText nombre;
    private Spinner spinnerProvincias;
    private Button seleccionarFechaBoton;
    private TextView seleccionarFechaText;
    private Button btnRegistrar;
    private RegistroUsuarios registro;
    private Double LatitudPersona;
    private Double LongitudPersona;
    private  Double [] VLatitud = {9.9328603358678, 10.016491249923705, 9.977504333213984, 9.998088767045822, 9.86297793342555, 9.991454796045499, 10.635840721519594};
    private  Double [] VLongitud = {-84.07950526683237, -84.21389019362014, -84.83082570321722, -84.11981971949604, 83.9233447908051, -83.04183689062727, -85.44059794533042};
    private int [] codigo = {1,2,3,4,5,6,7};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup raiz = (ViewGroup) inflater.inflate(R.layout.registro_fragment,container, false);

        identificacion = raiz.findViewById(R.id.identificacion);
        correoElectronico = raiz.findViewById(R.id.correoElectronicoRegistro);
        nombre = raiz.findViewById(R.id.nombre);
        spinnerProvincias = raiz.findViewById(R.id.spinnerProvincia);
        seleccionarFechaBoton = raiz.findViewById(R.id.pick_date_button);
        seleccionarFechaText = raiz.findViewById(R.id.show_selected_date);
        btnRegistrar = (Button) raiz.findViewById(R.id.botonRegistrar);
        btnRegistrar.setOnClickListener(this);
        registro = new RegistroUsuarios(this.getContext());
        LoginActivity activity = (LoginActivity) getActivity();
        LatitudPersona = activity.getLatitud();
        LongitudPersona = activity.getLongitud();

        MaterialDatePicker.Builder constructorfecha = MaterialDatePicker.Builder.datePicker();
        constructorfecha.setTitleText("Seleccione una fecha");
        final MaterialDatePicker materialSeleccionarFecha = constructorfecha.build();
        seleccionarFechaBoton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                materialSeleccionarFecha.show(getParentFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });
        materialSeleccionarFecha.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        seleccionarFechaText.setText("Fecha : " +
                                materialSeleccionarFecha.getHeaderText());
                    }
                });
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(getActivity(), R.array.Provincias, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerProvincias.setAdapter(adaptador);
        spinnerProvincias.setSelection(obtenerProvincia(LatitudPersona,LongitudPersona));
        return raiz;
    }

    //Calcula la distancia entre los dos puntos, se utiliza para ver la provincia mas cercana
    private double distancia(double lat1, double long1, double lat2, double long2){
        return Math.sqrt((lat1-lat2)*(lat1-lat2)+(long1-long2)*(long1-long2));
    }
    //Obtiene el codigo de la provincia mas cercana que se tenga, para asi ponerlo como default
    private int obtenerProvincia(double latitud, double longitud){
        int provActual = 1;
        double provinciaMasCercana = 9999999;
        for(int i=0; i<7;i++){
            double distancia = distancia(latitud,longitud,VLatitud[i],VLongitud[i]);
            if(distancia<provinciaMasCercana){
                provinciaMasCercana = distancia;
                provActual = i;
            }
        }
        return provActual;
    }

    
    private boolean esIdValida(String Id) {
        Pattern patron = Pattern.compile("^((\\d-\\d{3}-\\d{6}|\\d-\\d{4}-\\d{4}|\\d{9,10}))$");

        if (!patron.matcher(Id).matches()) {
            identificacion.setError("Identificacion inválida");
            return false;
        } else {
            identificacion.setError(null);
        }
        return true;
    }

    private boolean esCorreoValido(String Correo) {
        if (Correo.length() <= 0 || Correo.length() > 100 ||!Patterns.EMAIL_ADDRESS.matcher(Correo).matches()) {
            correoElectronico.setError("Correo inválido");
            return false;
        } else {
            correoElectronico.setError(null);
        }

        return true;
    }

    private boolean esNombreValido(String Nombre) {
        Pattern patron = Pattern.compile("^([a-zA-Z]{3,30}\\s[a-zA-Z]{3,30}[a-zA-Z\\s]*)$");

        if (!patron.matcher(Nombre).matches() || Nombre.length() <=0 || Nombre.length() > 100) {
            nombre.setError("Nombre inválido");
            return false;
        } else {
            nombre.setError(null);
        }

        return true;
    }

    private boolean esFechaValida(String Fecha) {
        if (Fecha.length() <= 0) {
            seleccionarFechaText.setError("Seleccione una fecha");
            return false;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
            LocalDate enteredDate = null;
            int difference = 0;
            try {
                enteredDate = LocalDate.parse(Fecha,formatter);
                difference = Period.between(enteredDate,LocalDate.now()).getDays();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(difference>=0){
                seleccionarFechaText.setError(null);
            }
            else{
                seleccionarFechaText.setError("Fecha invalida");
                return false;
            }

        }
        return true;
    }

    private boolean validarDatos() {
        String id = identificacion.getText().toString();
        String correo = correoElectronico.getText().toString();
        String name = nombre.getText().toString();
        String fecha = seleccionarFechaText.getText().toString().replace("Fecha : ","").replace("Fecha: ","");

        boolean idValida = esIdValida(id);
        boolean correoValido = esCorreoValido(correo);
        boolean nombreValido = esNombreValido(name);
        boolean fechaValida = esFechaValida(fecha);

        if (idValida && correoValido && nombreValido && fechaValida) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if(!isConnected()){
            Toast.makeText(getActivity(), "No hay conexión a internet", Toast.LENGTH_LONG).show();
        }else{


        if(validarDatos()) {

            switch (v.getId()) {
                case R.id.botonRegistrar:

                    Usuario usuario = new Usuario();
                    usuario.setIdentificacion(identificacion.getText().toString());
                    usuario.setNombre(nombre.getText().toString());
                    usuario.setCorreo(correoElectronico.getText().toString());
                    usuario.setPrimerLogin(1);
                    String password = RegistroUsuarios.generarContrasena();
                    usuario.setContrasena(password, true);
                    usuario.setEdad(seleccionarFechaText.getText().toString().replace("Fecha : ","").replace("Fecha: ",""));
                    usuario.setUbicacion(spinnerProvincias.getSelectedItem().toString());

                    if (usuario.isNull()) {
                        Toast.makeText(getActivity(), "ERROR: Revise que se ingresaran los datos correctos", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            if (registro.ingresarUsuario(usuario)) {

                                Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_LONG).show();
                              
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try {
                                            Email email = new Email();
                                            email.testMail(usuario.correo,"Bienvenido a la familia de Arce shopping","Hola "+ usuario.nombre.split(" ")[0].trim() +"!\n\n Gracias por registrarte en Arce's Shopping.\n\n Esta es tu contraseña de ingreso unico: "+password);
                                        } catch (Exception e) {
                                            Log.e("SendMail", e.getMessage(), e);
                                        }
                                    }

                                }).start();
                                Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_LONG).show();

                              //Se puede implementar funcionalidad para que pase a LOGIN pero hay que investigar por ser fragments
                            }else{
                                Toast.makeText(getActivity(), "ERROR: Usuario ya registrado", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            //Puede que ya exista o que hubo un problema al insertar así aque luego mejor cambiar el mensaje
                            Toast.makeText(getActivity(), "ERROR:Intente nuevamente", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
            }
        }
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

