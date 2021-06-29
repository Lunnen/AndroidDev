package com.example.w25finalsprint;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {


    private Button speakBtn;
    private TextView resultView, translationView;
    private ProgressBar loadingBar;
    private Spinner spinner;

    private DBhelper dBhelper;
    String translatedLanguageCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakBtn = findViewById(R.id.speakBtn);
        resultView = findViewById(R.id.resultView);
        translationView = findViewById(R.id.TranslationView);
        loadingBar = findViewById(R.id.progressBar);
        spinner = findViewById(R.id.spinner);

        speakBtn.setOnClickListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(19); //Default to 'German'

        dBhelper = new DBhelper(MainActivity.this);

    }

    @Override
    public void onClick(View v) {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        startActivityForResult(intent, 0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0 && resultCode== RESULT_OK){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            resultView.setText(results.get(0));

        }
    }

    public void prepareTranslation(View v){

        String sourceLanguage = TranslateLanguage.ENGLISH;
        String targetLanguage = spinner.getSelectedItem().toString();

        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(sourceLanguage)
                        .setTargetLanguage(getLanguageCode(targetLanguage))
                        .build();

        final Translator myTranslator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        loadingBar.setVisibility(View.VISIBLE);

        myTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                myTranslator.translate(resultView.getText().toString())
                                        .addOnSuccessListener(
                                                new OnSuccessListener() {
                                                    @Override
                                                    public void onSuccess(Object o) {
                                                        translationView.setText(o.toString());
                                                        translatedLanguageCode = getLanguageCode(targetLanguage);

                                                        myTranslator.close();
                                                        loadingBar.setVisibility(View.GONE);
                                                    }

                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Error.
                                                        // ...
                                                    }
                                                });
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                            }
                        });
    }

    private String getLanguageCode(String targetLanguage) {

        String tmpValue = "";
        
        switch(targetLanguage){
            case "Afrikaans": tmpValue = TranslateLanguage.AFRIKAANS; break;
            case "Arabic": tmpValue = TranslateLanguage.ARABIC; break;
            case "Belarusian": tmpValue = TranslateLanguage.BELARUSIAN; break;
            case "Bulgarian": tmpValue = TranslateLanguage.BULGARIAN; break;
            case "Bengali": tmpValue = TranslateLanguage.BENGALI; break;
            case "Catalan": tmpValue = TranslateLanguage.CATALAN; break;
            case "Czech": tmpValue = TranslateLanguage.CZECH; break;
            case "Welsh": tmpValue = TranslateLanguage.WELSH; break;
            case "Danish": tmpValue = TranslateLanguage.DANISH; break;
            case "German": tmpValue = TranslateLanguage.GERMAN; break;
            case "Greek": tmpValue = TranslateLanguage.GREEK; break;
            case "English": tmpValue = TranslateLanguage.ENGLISH; break;
            case "Esperanto": tmpValue = TranslateLanguage.ESPERANTO; break;
            case "Spanish": tmpValue = TranslateLanguage.SPANISH; break;
            case "Estonian": tmpValue = TranslateLanguage.ESTONIAN; break;
            case "Persian": tmpValue = TranslateLanguage.PERSIAN; break;
            case "Finnish": tmpValue = TranslateLanguage.FINNISH; break;
            case "French": tmpValue = TranslateLanguage.FRENCH; break;
            case "Irish": tmpValue = TranslateLanguage.IRISH; break;
            case "Galician": tmpValue = TranslateLanguage.GALICIAN; break;
            case "Gujarati": tmpValue = TranslateLanguage.GUJARATI; break;
            case "Hebrew": tmpValue = TranslateLanguage.HEBREW; break;
            case "Hindi": tmpValue = TranslateLanguage.HINDI; break;
            case "Croatian": tmpValue = TranslateLanguage.CROATIAN; break;
            case "Haitian": tmpValue = TranslateLanguage.HAITIAN_CREOLE; break;
            case "Hungarian": tmpValue = TranslateLanguage.HUNGARIAN; break;
            case "Indonesian": tmpValue = TranslateLanguage.INDONESIAN; break;
            case "Icelandic": tmpValue = TranslateLanguage.ICELANDIC; break;
            case "Italian": tmpValue = TranslateLanguage.ITALIAN; break;
            case "Japanese": tmpValue = TranslateLanguage.JAPANESE; break;
            case "Georgian": tmpValue = TranslateLanguage.GEORGIAN; break;
            case "Kannada": tmpValue = TranslateLanguage.KANNADA; break;
            case "Korean": tmpValue = TranslateLanguage.KOREAN; break;
            case "Lithuanian": tmpValue = TranslateLanguage.LITHUANIAN; break;
            case "Latvian": tmpValue = TranslateLanguage.LATVIAN; break;
            case "Macedonian": tmpValue = TranslateLanguage.MACEDONIAN; break;
            case "Marathi": tmpValue = TranslateLanguage.MARATHI; break;
            case "Malay": tmpValue = TranslateLanguage.MALAY; break;
            case "Maltese": tmpValue = TranslateLanguage.MALTESE; break;
            case "Dutch": tmpValue = TranslateLanguage.DUTCH; break;
            case "Norwegian": tmpValue = TranslateLanguage.NORWEGIAN; break;
            case "Polish": tmpValue = TranslateLanguage.POLISH; break;
            case "Portuguese": tmpValue = TranslateLanguage.PORTUGUESE; break;
            case "Romanian": tmpValue = TranslateLanguage.ROMANIAN; break;
            case "Russian": tmpValue = TranslateLanguage.RUSSIAN; break;
            case "Slovak": tmpValue = TranslateLanguage.SLOVAK; break;
            case "Slovenian": tmpValue = TranslateLanguage.SLOVENIAN; break;
            case "Albanian": tmpValue = TranslateLanguage.ALBANIAN; break;
            case "Swedish": tmpValue = TranslateLanguage.SWEDISH; break;
            case "Swahili": tmpValue = TranslateLanguage.SWAHILI; break;
            case "Tamil": tmpValue = TranslateLanguage.TAMIL; break;
            case "Telugu": tmpValue = TranslateLanguage.TELUGU; break;
            case "Thai": tmpValue = TranslateLanguage.THAI; break;
            case "Tagalog": tmpValue = TranslateLanguage.TAGALOG; break;
            case "Turkish": tmpValue = TranslateLanguage.TURKISH; break;
            case "Ukrainian": tmpValue = TranslateLanguage.UKRAINIAN; break;
            case "Urdu": tmpValue = TranslateLanguage.URDU; break;
            case "Vietnamese": tmpValue = TranslateLanguage.VIETNAMESE; break;
            case "Chinese": tmpValue = TranslateLanguage.CHINESE; break;
        }

        return tmpValue;
    }
    public void saveValues(View v){
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM @ HH:mm");
        Date date = new Date();

        dBhelper.addValue( formatter.format(date), resultView.getText().toString(), translationView.getText().toString(), translatedLanguageCode );
    }
    public void gotoDb(View view){
        Intent nextIntent = new Intent(this, DbShowActivity.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(nextIntent);

        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    // These two handles the State when changing between portrait/landscape mode
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("whatsSaid", resultView.getText().toString());
        outState.putString("translation", translationView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        resultView.setText(savedInstanceState.getString("whatsSaid"));
        translationView.setText(savedInstanceState.getString("translation"));
    }

}



