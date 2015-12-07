package com.example.ditmar.preguntados.utils;

import com.example.ditmar.preguntados.juego.DatosJuego;
import com.example.ditmar.preguntados.juego.Preguntas;
import com.example.ditmar.preguntados.juego.Respuestas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ditmar on 12/5/15.
 */
public class ParserJuego {
    private JSONObject juegoJson;
    private DatosJuego juegoDatos;
    public ParserJuego(JSONObject j)
    {
        this.juegoJson=j;
        parser();
    }
    private void parser()
    {
        try {
            String nombre=juegoJson.getString("nombre");
            String estado=juegoJson.getString("estado");
            Integer cantpreguntas=juegoJson.getInt("cantpreguntas");
            juegoDatos=new DatosJuego(nombre,estado,cantpreguntas);
            JSONArray preg=juegoJson.getJSONArray("preguntas");
            for(int i=0;i<preg.length();i++)
            {
                JSONObject auxp=(JSONObject)preg.get(i);
                String enun=auxp.getString("enunciado");
                String cat=auxp.getString("categoria");
                JSONArray res=auxp.getJSONArray("respuesta");
                Preguntas auxpreg=new Preguntas(enun,cat);
                for(int j=0;j<res.length();j++)
                {
                    JSONObject auxres=(JSONObject)res.get(j);
                    String respuest=auxres.getString("respuesta");
                    Boolean c=auxres.getBoolean("correcto");
                    Respuestas resp=new Respuestas(respuest,c);
                    auxpreg.addRespuesta(resp);
                }
                juegoDatos.addPregunta(auxpreg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public DatosJuego getParserJuego()
    {
        return this.juegoDatos;
    }
}
