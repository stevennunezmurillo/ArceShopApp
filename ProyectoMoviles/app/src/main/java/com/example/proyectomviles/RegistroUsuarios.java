package com.example.proyectomviles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Random;

public class RegistroUsuarios {

    Context contexto;
    Usuario usuario;
    SQLiteDatabase sql;
    String bd = "BDUsuarios";
    String table = "create table if not exists usuario(identificacion text primary key, edad text, nombre text, primerLogin text, correo text, ubicacion text, contrasena text)";
    private static String generadorContrasena = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890";
    private static int largoContrasena = 8;

    public RegistroUsuarios(Context context) {
        this.contexto = context;
        //Abrir o crear base de datos
        sql = context.openOrCreateDatabase(bd, context.MODE_PRIVATE, null);
        sql.execSQL(table);
        usuario = new Usuario();

    }

    public boolean ingresarUsuario(Usuario usuario) throws  Exception{
        if(buscar(usuario.getIdentificacion()) == 0){

            ContentValues contentValue= new ContentValues();
            contentValue.put("identificacion", usuario.getIdentificacion());
            contentValue.put("edad", usuario.getEdad());
            contentValue.put("nombre", usuario.getNombre());
            contentValue.put("primerLogin", usuario.getPrimerLogin());
            contentValue.put("correo", usuario.getCorreo());
            contentValue.put("ubicacion", usuario.getUbicacion());
            contentValue.put("contrasena", usuario.getContrasena());

            return(sql.insert("usuario", null, contentValue) > 0);
        }
        else{
            return false;
        }
    }

    public int buscar(String identificacion) throws Exception{
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();;
        listaUsuarios = usuariosRegistrados();
        for(Usuario u:listaUsuarios){
            if(u.getIdentificacion().equals(identificacion)){
                return 1;
            }
        }
        return 0;
    }

    //Metodo para generar de manera aleatoria una contrasena
    public static String generarContrasena(){
        String contrasenaGenerada ="";
        Random random = new Random();
        for(int i =0; i< largoContrasena;i++){
            contrasenaGenerada +=generadorContrasena.charAt(random.nextInt(generadorContrasena.length()));;
        }
        return contrasenaGenerada;
    }

    public ArrayList<Usuario> usuariosRegistrados() throws Exception {
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        Cursor cursor = sql.rawQuery("select * from usuario", null);
        if (cursor != null && cursor.moveToFirst()){

            do{

                Usuario usuario = new Usuario();
                usuario.setIdentificacion(cursor.getString(0));
                usuario.setPrimerLogin(cursor.getInt(3));
                usuario.setNombre(cursor.getString(2));
                usuario.setEdad(cursor.getString(1));
                usuario.setCorreo(cursor.getString(4));
                usuario.setUbicacion(cursor.getString(5));
                usuario.setContrasena(cursor.getString(6), false);
                listaUsuarios.add(usuario);

            }while(cursor.moveToNext());
        }
        return listaUsuarios;
    }

    //Método alternativo al login
    public boolean login(String correo, String contrasena) throws Exception {

        Cursor cursor = sql.rawQuery("select * from usuario", null);

        if(cursor != null && cursor.moveToFirst()){
            do {
                if(cursor.getString(4).equals(correo) && cursor.getString(6).equals(contrasena)){
                    return true;
                }
            }while(cursor.moveToNext());

        }
        return false;
    }

    public Usuario getUsuario(String correo,  String contrasena) throws Exception {
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        listaUsuarios = usuariosRegistrados();
        for (Usuario person : listaUsuarios) {
            String contraCifradaC = person.encriptar(contrasena);
            boolean validacionCorreo = person.getCorreo().toLowerCase().equals(correo.toLowerCase());
            boolean validacionContrasena = person.getContrasena().equals(contraCifradaC);
            if(validacionCorreo && validacionContrasena){
                return person;
            }
        }
        return null;
    }

    public boolean editarContrasena(Usuario usuario, String nueva_contrasena) throws  Exception{

        boolean actualizacion = false;
        String contrasena_encript = usuario.encriptar(nueva_contrasena);

        try{
            sql.execSQL("UPDATE " + "usuario" + " SET contrasena = '" + contrasena_encript + "',  primerLogin = '" + contrasena_encript + "'  WHERE identificacion = '" + usuario.getIdentificacion() + "' ");
            actualizacion = true;
        }catch(Exception ex){
           ex.toString();

        }
        return actualizacion;
    }
}
