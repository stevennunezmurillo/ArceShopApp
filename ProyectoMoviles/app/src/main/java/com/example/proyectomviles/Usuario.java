package com.example.proyectomviles;

import android.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Usuario {
    int primerLogin;
    String identificacion,nombre, correo, ubicacion, contrasena, edad;

    public Usuario(String edad, String identificacion, String nombre, String correo, String ubicacion, String contrasena, int primerLogin) {
        this.edad = edad;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
        this.ubicacion = ubicacion;
        this.contrasena = contrasena;
        this.primerLogin = primerLogin;
    }

    public boolean isNull(){
        if(edad.equals("") && identificacion.equals("") && primerLogin <= 0 && nombre.equals("") && correo.equals("") && ubicacion.equals("") && contrasena.equals("")){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "edad=" + edad +
                ", identificación='" + identificacion + '\'' +
                ", primerLogin='" + primerLogin + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", contrasena='"+ contrasena + '\'' +
                '}';
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getIdentificacion() { return identificacion; }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getPrimerLogin() {
        return primerLogin;
    }

    public void setPrimerLogin(int primerLogin) { this.primerLogin= primerLogin; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo.toLowerCase();
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena,boolean encriptacion) {
        try {
            if(encriptacion){
                String contrasenaEncriptada = encriptar(contrasena);
                this.contrasena = contrasenaEncriptada;
            }else{
                this.contrasena = contrasena;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Recibe de entrada la contrasena para encriptarse
    public static String encriptar(String contra) throws Exception{
        SecretKeySpec llave = generarLlave(contra);
        Cipher cifrado = Cipher.getInstance("AES");
        cifrado.init(Cipher.ENCRYPT_MODE, llave);
        byte[] bytesCifrados = cifrado.doFinal(contra.getBytes());
        String contraCifrada = Base64.encodeToString(bytesCifrados,Base64.DEFAULT);
        return contraCifrada;
    }

    //Metodo para generar la llave de encriptacion
    public static SecretKeySpec generarLlave(String contra) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = contra.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes,0,bytes.length);
        byte[] llave = digest.digest();
        SecretKeySpec llaveSecreta = new SecretKeySpec(llave, "AES");
        return llaveSecreta;
    }

    public Usuario(){
        this.edad = "";
        this.identificacion = "";
        this.nombre = "";
        this.correo = "";
        this.ubicacion = "";
        this.contrasena = "";
        this.primerLogin = 0;
    }

}
