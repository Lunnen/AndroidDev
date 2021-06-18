package com.example.w24weatherappservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {


    private String API_KEY = "6bf857e0a33bab6a90b16bb248eabb7d";
    private String language = "sv"; //SE = Sapmi, SV = Swedish

    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String languageString = "&lang=" + language;

    DBhelper dBhelper;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "clock", Toast.LENGTH_SHORT).show();

        String city = "Asarum";

        String queryText = (API_URL + "?q=" + city + "&appid=" + API_KEY + languageString + "&units=metric");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryText, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResp = new JSONObject(response);
                    JSONArray jsonArray = jsonResp.getJSONArray("weather");
                    JSONObject objectWeather = jsonArray.getJSONObject(0);
                    String desc = objectWeather.getString("description");
                    JSONObject jsonMain = jsonResp.getJSONObject("main");
                    String cityName = jsonResp.getString("name");

                    double temperature = jsonMain.getDouble("temp");

                    JSONObject jsonObjectSys = jsonResp.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");

                    /* DB part */
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    Log.d("BUGG", (formatter.format(date) +  (cityName + ", " + countryName) + (temperature + " °C") + desc));


                    dBhelper = new DBhelper(context);
                    dBhelper.addValue(formatter.format(date), (cityName + ", " + countryName), (temperature + " °C"), desc);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}

