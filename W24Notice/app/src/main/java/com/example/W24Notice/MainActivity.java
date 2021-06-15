package com.example.W24Notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNote;
    private TextView title, message, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNote= findViewById(R.id.button);
        btnNote.setOnClickListener(this);

        title = findViewById(R.id.editTextTitle);
        message = findViewById(R.id.editTextMessage);
        imageUrl = findViewById(R.id.editTextImage);
    }

    @Override
    public void onClick(View v) {
        startService();
    }

    public void startService() {

        String editTitle = title.getText().toString();
        String editMessage = message.getText().toString();
        String editImage = imageUrl.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("title", editTitle);
        bundle.putString("message", editMessage);
        bundle.putString("image", editImage);

        Intent intent  = new Intent(this, ForegroundService.class);
        intent.putExtra("myInfo", bundle);
        ContextCompat.startForegroundService(this,intent);

    }
}