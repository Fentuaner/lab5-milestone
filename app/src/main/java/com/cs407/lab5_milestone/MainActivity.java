package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void onClick(View view) {
        EditText usernameName = (EditText) findViewById(R.id.Username);
        Intent content = new Intent(this, MainActivity2.class);
        startActivity(content);
            // 1. Get the username from the Edittext component and assign it a variable
            // 2. Adding username variable to sharedPreferences using the below given code
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        sharedPreferences.edit().putString("username", usernameName.getText().toString()).apply();
            // 3. Start the notes page or the second activity
    }

    /*@Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.logoutItem) {
            SharedPreferences sharedPreferences = getSharedPreferences ( "com.cs407.lab5_milestone", MODE_PRIVATE);
            String userName = sharedPreferences.getString("username","");
            sharedPreferences.edit() .clear() .apply();
            goToMainActivity );
            return true;

        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //String Username = sharedPreferences.getString(s: "username", s1: "")

        String username = "username ";
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        if(sharedPreferences.getString ("Username" ,  "") != "") {
        // 1. Implies that the username exists in SharedPreferences. The user did not log out when closing the
        // application
        // 2. Get the value of the logged in username from SharedPreferences using the
        // sharedPreferences. getString (username,"").
        // 3. Using Intents start the second screen, Figure 2 activity
            Intent content = new Intent(this, MainActivity2.class);
            startActivity(content);
        }else {
            //1. This implies the there is no username in sharedPreferences, no username key set.
            //2. Start the login screen activity that is the main activity
            setContentView(R.layout.activity_main);
        }
    }
}


