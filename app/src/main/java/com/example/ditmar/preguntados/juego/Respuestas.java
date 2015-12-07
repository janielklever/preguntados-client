package com.example.ditmar.preguntados.juego;

/**
 * Created by ditmar on 12/5/15.
 */
public class Respuestas {
    private String respuesta;
    private Boolean correcto;
    public Respuestas(String r,Boolean c)
    {
        this.respuesta = r;
        this.correcto = c;
    }
    public String getRespuesta()
    {
        return this.respuesta;
    }
    public Boolean getCorrecto ()
    {
        return this.correcto;
    }
}