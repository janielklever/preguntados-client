package com.example.ditmar.preguntados;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.ditmar.preguntados.juego.DatosJuego;
import com.example.ditmar.preguntados.utils.JuegosAdapter;
import com.example.ditmar.preguntados.utils.ParserJuego;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class SalaPrincipal extends AppCompatActivity implements View.OnClickListener {
    private Socket nSocket;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
            nSocket = IO.socket(Constantes.SERVER);
            nSocket.on("partida", crear_partida);
            nSocket.on("emit_juego", emit_juego);
            nSocket.on("sala_join", sala_join);
            nSocket.emit("sala_principal");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        LoadComponents();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    EditText nombre, numero;
    Button play;
    ListView juegoslist;
    private void LoadComponents() {
        nombre = (EditText) this.findViewById(R.id.nombrejuego);
        numero = (EditText) this.findViewById(R.id.numbertxt);
        juegoslist=(ListView)this.findViewById(R.id.juegoscreados);
        play = (Button) this.findViewById(R.id.play);
        play.setOnClickListener(this);

        TabHost tab = (TabHost) this.findViewById(R.id.tabHost);
        tab.setup();
        tab.addTab(tab.newTabSpec("crear").setIndicator("Crear").setContent(R.id.create));

        tab.addTab(tab.newTabSpec("join").setIndicator("Unirse").setContent(R.id.join));
        tab.setCurrentTab(0);
        listadejuegos=new ArrayList<DatosJuego>();
    }

    private Activity principal = this;
    Emitter.Listener sala_join=new Emitter.Listener() {
        @Override
        public void call(final Object... datos) {
            principal.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject server = (JSONObject)datos[0];
                    try {
                        Toast.makeText(principal, server.getString("msn"),Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    protected ArrayList<DatosJuego> listadejuegos;
    Emitter.Listener emit_juego = new Emitter.Listener() {
        @Override
        public void call(final Object... ss) {

            principal.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(principal,"JUEGO CREADO ",Toast.LENGTH_LONG).show();
                    JSONArray juego = (JSONArray) ss[0];
                    try {
                        InformacionJuego.DATA_JUEGO = (JSONObject) juego.get(0);
                        ParserJuego parser = new ParserJuego(InformacionJuego.DATA_JUEGO);
                        DatosJuego juegosdatos=parser.getParserJuego();
                        listadejuegos.add(juegosdatos);
                        JuegosAdapter adapter=new JuegosAdapter(listadejuegos,principal);
                        juegoslist.setAdapter(adapter);
                        //Toast.makeText(principal, juego.getString("msn"),Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //
                }
            });
        }
    };

    Emitter.Listener crear_partida = new Emitter.Listener() {
        @Override
        public void call(final Object... argss) {
            principal.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray juego = (JSONArray) argss[0];


                    if (juego.length() == 1) {
                        try {
                            Toast.makeText(principal, "Juego creado!", Toast.LENGTH_LONG).show();
                            InformacionJuego.DATA_JUEGO = (JSONObject) juego.get(0);
                            Intent PG = new Intent(principal, PGame.class);
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
        if (nombre.getText().toString().equals("")) {
            Toast.makeText(this, "No puedes crear juegos sin nombre", Toast.LENGTH_LONG).show();
            return;
        }

        String number = numero.getText().toString();
        JSONObject parametros = new JSONObject();

        try {
            parametros.put("nombre", nombre.getText().toString());
            parametros.put("estado", "nuevo");
            parametros.put("cantpreguntas", number);
            nSocket.emit("crear_partida", parametros);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SalaPrincipal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ditmar.preguntados/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SalaPrincipal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ditmar.preguntados/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
