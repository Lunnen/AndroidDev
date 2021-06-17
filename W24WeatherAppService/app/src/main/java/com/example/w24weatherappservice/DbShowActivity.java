package com.example.w24weatherappservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class DbShowActivity extends AppCompatActivity {

    private DBhelper dBhelper;

    private RecyclerView valueView;
    private ValueAdapter valueAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_show2);

        // setup the Db
        dBhelper = new DBhelper(DbShowActivity.this);

        ArrayList<WeatherBean> myValues = new ArrayList<WeatherBean>();

        myValues =  dBhelper.getValue(myValues);

        Log.d("detta", myValues.toString());

        // setup for the  RecyclerView
        valueView = findViewById(R.id.showDbValues);


        valueView.setLayoutManager( new LinearLayoutManager(this));


        //ValueAdapter valueAdapter = new ValueAdapter();
        valueAdapter = new ValueAdapter(myValues);

        valueView.setAdapter(valueAdapter);

    }
    public void gotoView(View view){
        Intent nextIntent = new Intent(this, MainActivity.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nextIntent);

        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void runDeleteAll(View view){
        dBhelper.deleteAll();
    }
}