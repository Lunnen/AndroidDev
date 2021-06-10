package com.example.w23lifecycle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ThirdActivity extends MenuOptions {

    TextView textName, textTitle, textKnowledge, textGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        textName = findViewById(R.id.textName);
        textTitle = findViewById(R.id.textTitle);
        textKnowledge = findViewById(R.id.textKnowledge);
        textGender = findViewById(R.id.textGender);


        SharedPreferences sharedPreferences = this.getSharedPreferences("w23", MODE_PRIVATE);
        textName.setText(sharedPreferences.getString("editName","Nothing fund"));
        textTitle.setText(sharedPreferences.getString("editWorkTitle","Nothing fund"));
        textKnowledge.setText(sharedPreferences.getString("editKnowledge","Nothing fund"));
        textGender.setText(sharedPreferences.getString("gender","Nothing fund"));

        /* No need for bundled intent with sharedPref <---
        Intent intent = getIntent();

        Bundle editBundle = intent.getBundleExtra("thisBundle");
        String incomingName = editBundle.getString("name");
        String incomingTitle = editBundle.getString("title");
        String incomingKnowledge = editBundle.getString("knowledge");
        String incomingGender = editBundle.getString("gender");

        textName.setText(incomingName);
        textTitle.setText(incomingTitle);
        textKnowledge.setText(incomingKnowledge);
        textGender.setText(incomingGender);
         */

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("textName", textName.getText().toString());
        outState.putString("textTitle", textTitle.getText().toString());
        outState.putString("textKnowledge", textKnowledge.getText().toString());
        outState.putString("textGender", textGender.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        textName.setText(savedInstanceState.getString("textName"));
        textTitle.setText(savedInstanceState.getString("textTitle"));
        textKnowledge.setText(savedInstanceState.getString("textKnowledge"));
        textGender.setText(savedInstanceState.getString("textGender"));

    }
}
