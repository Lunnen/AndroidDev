package com.example.w23lifecycle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.w23lifecycle.data.LoginDataSource;

import static com.example.w23lifecycle.R.*;

public class FormActivity extends MenuOptions {

    EditText editName, editWorkTitle, editKnowledge;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_form);


        editName = findViewById(id.editTextTextPersonName);
        editWorkTitle = findViewById(id.editTextTextPersonWorkTitle);
        editKnowledge = findViewById(id.editTextTextPersonKnowledge);

        /* RESTORE process - if something's been saved */
        SharedPreferences sharedPreferences = this.getSharedPreferences("w23", MODE_PRIVATE);
        editName.setText(sharedPreferences.getString("editName",""));
        editWorkTitle.setText(sharedPreferences.getString("editWorkTitle",""));
        editKnowledge.setText(sharedPreferences.getString("editKnowledge",""));
        gender = sharedPreferences.getString("gender","");

        RadioButton box;
        switch(gender) {
            case "male":
                box = findViewById(id.isMale);
                box.setChecked(true);
                break;
            case "female":
                box = findViewById(id.isFemale);
                box.setChecked(true);
                break;
            case "other":
                box = findViewById(id.isOtherGender);
                box.setChecked(true);
                break;
        }
        // -------------------------------------------------------

    }

    public void onClick(View view) {

        Switch gdprSwitch = findViewById(id.gdprSwitch);

        if(gdprSwitch.isChecked()){

            RadioGroup thisRg = findViewById(id.radioGroup);
            switch(thisRg.getCheckedRadioButtonId()) {
                case R.id.isMale:
                    gender = "male";
                    break;
                case R.id.isFemale:
                    gender = "female";
                    break;
                case R.id.isOtherGender:
                    gender = "other";
                    break;
            }

            SharedPreferences sharedPreferences = this.getSharedPreferences("w23", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("editName",editName.getText().toString());
            editor.putString("editWorkTitle",editWorkTitle.getText().toString());
            editor.putString("editKnowledge",editKnowledge.getText().toString());
            editor.putString("gender",gender);
            editor.apply();

            /*
            String thisNameValue = editName.getText().toString();
            String thisTitle = editWorkTitle.getText().toString();
            String thisKnowledge = editKnowledge.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("name", thisNameValue);
            bundle.putString("title", thisTitle);
            bundle.putString("knowledge", thisKnowledge);
            bundle.putString("gender", String.valueOf(gender));

            thirdIntent.putExtra("thisBundle", bundle);
            */

            Intent thirdIntent = new Intent(this, ThirdActivity.class);
            thirdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(thirdIntent);

            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        }
        else {
            Toast.makeText(getApplicationContext(), getString(string.gdprAcceptance), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("editName", editName.getText().toString());
        outState.putString("editWorkTitle", editWorkTitle.getText().toString());
        outState.putString("editKnowledge", editKnowledge.getText().toString());
        outState.putString("gender", gender);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        editName.setText(savedInstanceState.getString("editName"));
        editWorkTitle.setText(savedInstanceState.getString("editWorkTitle"));
        editKnowledge.setText(savedInstanceState.getString("editKnowledge"));
        gender = savedInstanceState.getString("gender");

    }
}