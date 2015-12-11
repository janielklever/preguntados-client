package com.example.ditmar.preguntados.juego;

import java.util.ArrayList;

/**
 * Created by ditmar on 12/5/15.
 */
public class DatosJuego {
    private String nombre;
    private String estado;
    private Integer cantpreguntas;
    private ArrayList<Preguntas> preguntas;
    private ArrayList<Usuarios> usuarios;
    public DatosJuego(String n,String e,Integer cant)
    {
        this.nombre=n;
        this.estado=e;
        this.cantpreguntas=cant;
        usuarios=new ArrayList<Usuarios>();
        preguntas=new ArrayList<Preguntas>();
    }
    public void addUsuarios(Usuarios p)
    {
        usuarios.add(p);
    }

    public void addPregunta(Preguntas p)
    {
        preguntas.add(p);
    }
    public String getNombre()
    {
        return this.nombre;

    }
    public String getEstado()
    {
        return this.estado;
    }
    public Integer getCantPreguntas()
    {
        return this.cantpreguntas;
    }
    public ArrayList<Preguntas> getPreguntas()
    {
        return this.preguntas;
    }
    public ArrayList<Usuarios> getUsuarios()
    {
        return this.usuarios;
    }

}
