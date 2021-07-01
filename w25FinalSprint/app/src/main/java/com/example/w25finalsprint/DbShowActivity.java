package com.example.w25finalsprint;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class DbShowActivity extends AppCompatActivity {


    private ImageButton btnSpeak;
    private DBhelper dBhelper;

    private RecyclerView valueView;
    private ValueAdapter valueAdapter;
    ArrayList<TranslatedBean> myValues = new ArrayList<TranslatedBean>();

    TextToSpeech toSpeech;

    private static Bundle mBundleRecyclerViewState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_show);

        btnSpeak = findViewById(R.id.btn_listen);

        // setup the Db
        dBhelper = new DBhelper(DbShowActivity.this);
        myValues =  dBhelper.getValue(myValues);

        // setup for the  RecyclerView
        valueView = findViewById(R.id.showDbValues);
        valueView.setLayoutManager( new LinearLayoutManager(this));

        valueAdapter = new ValueAdapter(myValues);

        valueView.setAdapter(valueAdapter);

        //btnSpeak.setOnClickListener(this);

        toSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                try {
                    toSpeech.setLanguage(new Locale("en_GB"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("speak", "onInit: " + TextToSpeech.ERROR);
                }
            }
        });

        valueAdapter.setOnItemClickListener(new ValueAdapter.OnItemClickListener() {
            /*
            @Override
            public void onItemClick(int position) {
                toSpeechOrg.speak(myValues.get(position).getOriginText(), TextToSpeech.QUEUE_FLUSH, null);
            }*/

            @Override
            public void onPushPlayOrg(int position) {
                toSpeech.setLanguage(new Locale("en_GB"));
                toSpeech.speak(myValues.get(position).getOriginText(), TextToSpeech.QUEUE_FLUSH, null);
            }
            @Override
            public void onPushPlayOther(int position) {

                Log.d("LANG", "onPushPlayOther: " + myValues.get(position).getTranslatedTextLang());

                String languageFromDb = myValues.get(position).getTranslatedTextLang();

                // Norway has two languages, specify to Norwegian Bokmål.
                if(languageFromDb.equals("no")){
                    languageFromDb = "nb";
                }

                toSpeech.setLanguage( new Locale(languageFromDb) );
                toSpeech.speak(myValues.get(position).getTranslatedText(), TextToSpeech.QUEUE_FLUSH, null);
            }
            @Override
            public void onPushDeleteItem(int position) {

                new AlertDialog.Builder(DbShowActivity.this)
                        .setTitle(R.string.delSpecDb)
                        .setMessage(R.string.delSpecDbSure)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                dBhelper.deleteEntry(myValues.get(position).getTranslatedText());
                                myValues.remove(position); //Clear arraylist
                                valueAdapter.notifyItemChanged(position); //notify adapter of data-change, which clears the view
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        toSpeech.stop();
        toSpeech.shutdown();
    }

    public void gotoView(View view){
        Intent nextIntent = new Intent(this, MainActivity.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(nextIntent);

        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void runDeleteAll(View view){

        new AlertDialog.Builder(this)
                .setTitle(R.string.delAllDb)
                .setMessage(R.string.delAllDbSure)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dBhelper.deleteAll();
                        myValues.clear(); //Clear arraylist
                        valueAdapter.notifyDataSetChanged(); //notify adapter of data-change, which clears the view
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // These two handles the State when changing between portrait/landscape mode
    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = valueView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable("recycler_state", listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable("recycler_state");
            valueView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

}