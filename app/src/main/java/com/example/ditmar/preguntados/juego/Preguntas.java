package com.example.ditmar.preguntados.juego;

import java.util.ArrayList;

/**
 * Created by ditmar on 12/5/15.
 */
public class Preguntas {
    private String enunciado;
    private String categoria;
    private ArrayList<Respuestas> respuesta;
    public Preguntas(String e,String cat)
    {
        this.enunciado=e;
        this.categoria=cat;
        respuesta=new ArrayList<Respuestas>();
    }
    public void addRespuesta(Respuestas r)
    {
        respuesta.add(r);
    }
    public String getEnunciado()
    {
        return this.enunciado;
    }
    public String getCategoria()
    {
        return this.categoria;
    }
    public ArrayList<Respuestas> getRespuesta()
    {
        return this.respuesta;
    }
}
