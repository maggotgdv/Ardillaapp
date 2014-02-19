package com.avellana.napo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import android.util.Log;

public class data {
	static String dominio = "http://192.168.1.110/api/v1/";
	static String Final ="format=json";
    public JSONArray getCategorias(){
     	String result = "";//aca se va a guardar todo el resultado
     	InputStream isr = null;//crea el lector
     	try{
             HttpClient httpclient = new DefaultHttpClient();//cera el cliente http
             HttpGet httppost = new HttpGet(dominio+"productCat/?"+Final); //la ruta del script
             HttpResponse response = httpclient.execute(httppost);//ejecuta el script
             HttpEntity entity = response.getEntity();//recoge el resultado
             isr = entity.getContent();//jala el contenido del entity
	     }
	     catch(Exception e){
	             Log.e("log_tag", "Error en coneccion http "+e.toString());//si pasa algo malo
	     }
	     //convert response to string
	     try{
	             BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);//lo formatea al buffer para la lectura adecuada
	             StringBuilder sb = new StringBuilder();//creamos un armador de strings
	             String line = null;//se crea un string a recibir
	             while ((line = reader.readLine()) != null) {//mientras tengamos texto
	                     sb.append(line + "\n");//y lo separa dato por dato
	             }
	             isr.close();//cierra el lector
	
	             result=sb.toString();//y copia todas a un solo string
	     }
	     catch(Exception e){
	             Log.e("log_tag", "Error en convertir result "+e.toString());//si tiene algun error
	
	     }
	     JSONArray jArray = null;
	     //parse json data
	    try {
	 	   jArray = new JSONArray(result);//crea un JSONArray desde result
	    } catch (Exception e) {
	 	   Log.e("log_tag", "Error Al Pasar La Data "+e.toString());//si tiene errores
	
	    }
		return jArray;
	 }


}
