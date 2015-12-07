package com.example.ditmar.preguntados;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernametxt;
    private EditText passwordtxt;

    //creacion del socket
    private Socket nSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            nSocket=IO.socket(Constantes.SERVER);
            nSocket.connect();
            nSocket.on("onLog",onLog);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LoadComponents();
    }
    public void LoadComponents()
    {
        Button loginbtn=(Button)this.findViewById(R.id.button);
        Button registerbtn=(Button)this.findViewById(R.id.button2);
        usernametxt=(EditText)this.findViewById(R.id.editText);
        passwordtxt=(EditText)this.findViewById(R.id.editText2);
        loginbtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private Activity pricipal=this;
    Emitter.Listener onLog=new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            pricipal.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject resultado=(JSONObject)args[0];
                    try {

                        Boolean status=resultado.getBoolean("status");
                        String msn=resultado.getString("msn");

                        if(status)
                        {
                            Intent sala=new Intent(pricipal,SalaPrincipal.class);
                            pricipal.startActivity(sala);
                        }else{
                            Toast.makeText(pricipal,msn,Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    @Override
    public void onClick(View v) {
        if(usernametxt.getText().toString().equals(""))
        {
            Toast.makeText(this,"El Campo de usuario no puede ser Vacio",Toast.LENGTH_LONG).show();
            return;
        }
        JSONObject parametros=new JSONObject();
        try {
            parametros.put("email",usernametxt.getText().toString());
            parametros.put("password", passwordtxt.getText().toString());
            nSocket.emit("onLog", parametros);
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
