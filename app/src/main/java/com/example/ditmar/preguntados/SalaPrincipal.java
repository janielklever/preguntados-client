package com.example.ditmar.preguntados;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SalaPrincipal extends AppCompatActivity implements  View.OnClickListener {
    private Socket nSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_principal);
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
        try {
            nSocket= IO.socket(Constantes.SERVER);
            nSocket.on("crear_partida",crear_partida);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        LoadComponents();

    }
    EditText nombre,numero;
    Button play;
    private void LoadComponents()
    {
        nombre=(EditText)this.findViewById(R.id.nombrejuego);
        numero=(EditText)this.findViewById(R.id.numbertxt);
        play=(Button)this.findViewById(R.id.play);
        play.setOnClickListener(this);
    }
    private Activity principal=this;
    Emitter.Listener crear_partida=new Emitter.Listener() {
        @Override
        public void call(final Object... argss) {
            principal.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray juego=(JSONArray)argss[0];


                    if(juego.length()==1)
                    {
                        try {
                            Toast.makeText(principal,"Juego creado!",Toast.LENGTH_LONG).show();
                            InformacionJuego.DATA_JUEGO=(JSONObject)juego.get(0);
                            Intent PG=new Intent(principal,PGame.class);
                            principal.startActivity(PG);
                            Log.d("PARAMETROS DE JUEGO ", juego.get(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    };

    @Override
    public void onClick(View v) {
        if(nombre.getText().toString().equals(""))
        {
            Toast.makeText(this,"No puedes crear juegos sin nombre", Toast.LENGTH_LONG).show();
            return;
        }

        String number=numero.getText().toString();
        JSONObject parametros=new JSONObject();

        try {
            parametros.put("nombre",nombre.getText().toString());
            parametros.put("estado","nuevo");
            parametros.put("cantpreguntas",number);
            nSocket.emit("crear_partida", parametros);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
