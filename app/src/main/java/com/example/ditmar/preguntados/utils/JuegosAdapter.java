package com.example.ditmar.preguntados.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ditmar.preguntados.R;
import com.example.ditmar.preguntados.juego.DatosJuego;

import java.util.ArrayList;

/**
 * Created by ditmar on 12/11/15.
 */
public class JuegosAdapter extends BaseAdapter {
    private ArrayList<DatosJuego>JUEGOS;
    private Activity contexto;
    public JuegosAdapter(ArrayList<DatosJuego> lista,Activity co)
    {
        JUEGOS=lista;
        contexto=co;

    }
    @Override
    public int getCount() {
        return JUEGOS.size();
    }

    @Override
    public Object getItem(int position) {
        return JUEGOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root=convertView;
        if(root==null)
        {
            LayoutInflater l=(LayoutInflater)this.contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            root=l.inflate(R.layout.itemjuegos,null);

        }
        TextView nombre=(TextView)root.findViewById(R.id.nombres_juegoss);
        TextView usuario=(TextView)root.findViewById(R.id.usuariojuego);
        TextView preguntas=(TextView)root.findViewById(R.id.preguntasjuego);
        nombre.setText(JUEGOS.get(position).getNombre());
        usuario.setText(JUEGOS.get(position).getUsuarios().get(0).nombres);
        //preguntas.setText(JUEGOS.get(position).getCantPreguntas());
        return root;
    }
}
