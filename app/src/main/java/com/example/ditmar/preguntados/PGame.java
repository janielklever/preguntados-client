package com.example.ditmar.preguntados;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ditmar.preguntados.juego.DatosJuego;
import com.example.ditmar.preguntados.utils.ParserJuego;

public class PGame extends AppCompatActivity implements  View.OnClickListener {

    DatosJuego juego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ParserJuego parser=new ParserJuego(InformacionJuego.DATA_JUEGO);
        juego=parser.getParserJuego();

        //juego.getPreguntas().get(0).getRespuesta().get(0);

        LoadComponets();
    }
    private void LoadComponets()
    {
        TextView nombre=(TextView)this.findViewById(R.id.nombre_juego);
        nombre.setText(juego.getNombre());
        Button inicio=(Button)this.findViewById(R.id.iniciobtn);
        inicio.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
