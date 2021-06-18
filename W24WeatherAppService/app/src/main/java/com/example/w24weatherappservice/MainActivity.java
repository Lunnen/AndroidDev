package com.example.w24weatherappservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText chosenCity;
    TextView result, showTempMain, showTempMin, showTempMax, showTempFeel, showWeatherInfo, showSunset, showSunrise;
    ImageView weatherIcon, sunriseImage, sunsetImage;

    private String API_KEY = "6bf857e0a33bab6a90b16bb248eabb7d";
    private String language = "sv"; //SE = Sapmi, SV = Swedish

    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String languageString = "&lang=" + language;

    private DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chosenCity = findViewById(R.id.chosenCity);
        result = findViewById(R.id.outputResult);
        weatherIcon = findViewById(R.id.weatherIcon);
        showTempMain = findViewById(R.id.showTempMain);
        showTempMin = findViewById(R.id.showTempMin);
        showTempMax = findViewById(R.id.showTempMax);
        showTempFeel = findViewById(R.id.showTempFeel);
        showWeatherInfo = findViewById(R.id.showWeatherInfo);
        showWeatherInfo = findViewById(R.id.showWeatherInfo);
        showSunrise = findViewById(R.id.showSunriseTime);
        showSunset = findViewById(R.id.showSunsetTime);
        sunriseImage = findViewById(R.id.sunriseImage);
        sunsetImage = findViewById(R.id.sunsetImage);

        dBhelper = new DBhelper(MainActivity.this);

        setAlarm();
    }

    public void fetchWeatherData(View view){

        String city = chosenCity.getText().toString().trim();

        String queryText = (API_URL + "?q=" + city + "&appid=" + API_KEY + languageString + "&units=metric");

        if (city.equals("")) {
            result.setText("Vänligen ange en plats!");
        }
        else{
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryText, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                String output = "";

                try {
                    JSONObject jsonResp = new JSONObject(response);
                    JSONArray jsonArray = jsonResp.getJSONArray("weather");
                    JSONObject objectWeather = jsonArray.getJSONObject(0);
                    String desc = objectWeather.getString("description");
                    JSONObject jsonMain = jsonResp.getJSONObject("main");
                    String cityName = jsonResp.getString("name");
                    //int timezoneValue = jsonResp.getInt("timezone");

                    double temperature = jsonMain.getDouble("temp");
                    double feelsLike = jsonMain.getDouble("feels_like");
                    double tempMin = jsonMain.getDouble("temp_min");
                    double tempMax = jsonMain.getDouble("temp_max");
                    double pressure = jsonMain.getDouble("pressure");
                    int humidity = jsonMain.getInt("humidity");

                    JSONObject objectWind = jsonResp.getJSONObject("wind");
                    String wind = objectWind.getString("speed");

                    JSONObject jsonObjectClouds = jsonResp.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");

                    JSONObject jsonObjectSys = jsonResp.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    int sunriseAsUnixTime = jsonObjectSys.getInt("sunrise");
                    int sunsetAsUnixTime = jsonObjectSys.getInt("sunset");

                    Date sunriseDate = new Date((sunriseAsUnixTime) * 1000L);
                    Date sunsetDate = new Date((sunsetAsUnixTime) * 1000L);

                    String weatherIconValue = objectWeather.getString("icon");
                    String weatherIconURL = "https://openweathermap.org/img/w/" + weatherIconValue + ".png"; // Nice pics, but NOT zoomable/able to enlarge
                    //String weatherIconURL = "https://openweathermap.org/img/wn/" + weatherIconValue + "@4x.png"; // Bad pics, but able to enlarge/zoom in
                    loadImage(weatherIconURL, weatherIcon);

                    output = "Fuktighet: " + humidity + " %"
                            + "\nVindhastighet: " + wind + " m/s"
                            + "\nMolnighet: " + clouds + " %"
                            + "\nTryck: " + pressure + " hPa";

                    result.setText(output);

                    showTempMain.setText(temperature + " °C");
                    showTempFeel.setText("Upplevd temp. " + feelsLike + " °C");

                    showTempMin.setText("min: \n" + tempMin + " °C");
                    showTempMax.setText("max: \n" + tempMax + " °C");

                    sunriseImage.setImageResource(R.drawable.sunrise);
                    sunsetImage.setImageResource(R.drawable.sunset);
                    showSunrise.setText(formatTime(sunriseDate));
                    showSunset.setText(formatTime(sunsetDate));

                    showWeatherInfo.setText("i " + cityName + ", " + countryName
                                                            + "\när det " + desc);
                    /* DB part */
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    dBhelper.addValue(formatter.format(date), (cityName + ", " + countryName), (temperature + " °C"), desc);

                    /* Notification service => Image doesn't work (needs to be BITMAP) */
                    String sendTitle = (cityName + ", " + countryName);
                    String sendMessage = desc;
                    String sendImg = weatherIconURL;

                    startNotificationService(sendTitle,sendMessage,sendImg);
                    //--------------------------------------------

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        }
    }

    private void loadImage(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }
    public static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(dateObject);
    }

    public void startNotificationService(String title, String message, String imgURL) {

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("image", imgURL);


        Log.d( "inNote: ", bundle.toString());

        Intent intent  = new Intent(this, ForegroundService.class);
        intent.putExtra("myInfo", bundle);
        ContextCompat.startForegroundService(this,intent);

    }
    public void gotoView(View view){
        Intent nextIntent = new Intent(this, DbShowActivity.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nextIntent);

        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);

        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

        long firstTime = 5000 + SystemClock.elapsedRealtime();

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 60000, sender);}

}