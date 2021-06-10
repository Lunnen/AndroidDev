package com.example.w23lifecycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.w23lifecycle.ui.login.LoginActivity;

public class MenuOptions extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:

                SharedPreferences sharedPreferences2 = this.getSharedPreferences("w23", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putBoolean("userLoggedIn", false);
                editor2.apply();

                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);

                return true;
            case R.id.form:

                Intent formIntent = new Intent(this, FormActivity.class);
                formIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(formIntent);

                return true;
            case R.id.third:

                Intent dataViewIntent = new Intent(this, ThirdActivity.class);
                dataViewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dataViewIntent);

                return true;
            case R.id.clearData:

                SharedPreferences sharedPreferences = this.getSharedPreferences("w23", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
