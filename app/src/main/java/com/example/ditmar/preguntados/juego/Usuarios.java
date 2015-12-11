package com.example.ditmar.preguntados.juego;

/**
 * Created by ditmar on 12/7/15.
 */
public class Usuarios {
    public String email;
    public String nombres;
    public Puntajes puntaje;
    public Usuarios(String e,String n,Puntajes p)
    {
        this.email=e;
        this.nombres=n;
        this.puntaje=p;
    }
}
