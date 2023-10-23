package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class noteWriting extends AppCompatActivity {
    private int noteId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);

        // 1. Get EditText view
        // 2. Get intent coming here from screen 2
        // 3. Get the value of integer "noteid: from intent
        // 4. Initialize class car noteid with the value from intent
        EditText editText = (EditText) findViewById(R.id.note);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            Context context = getApplicationContext();
            SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
            DBHelper dbHelper = new DBHelper(sqLiteDatabase);
            // Displaying content of the note by retrieving from ArrayList in SecondActivity
            // 5. Use editText.setText()to display the contents of this note on the screen
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
            String name = sharedPreferences.getString("username", "");
            ArrayList<Notes> notesList = dbHelper.readNotes(name);
            String noteContent = notesList.get(noteId).getContent();
            editText.setText(noteContent);
        }


        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. Get editText view and the content that user entered
                EditText viewById = (EditText) findViewById(R.id.note);
                String content = viewById.getText().toString();
                // 2. Initialize SQLiteDatabase instance.
                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
                // 3. Initialize DBHelper class.
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);
                // 4. Set username in the following variable by fetching it from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                // Save data in Database

                String s;
                DateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY HH:mm:ss");
                String date = dateFormat.format(new Date());
                Log.i("Info", "Printing noteid before using in condition" + noteId);
                if (noteId == -1) {
                    s = "NOTES_" + (dbHelper.readNotes(username).size() + 1);
                    Log.i("info", "printing content to be saved" + content);

                    context = getApplicationContext();
                    sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
                    dbHelper = new DBHelper(sqLiteDatabase);

                    dbHelper.saveNotes(username, s, date, content);
                } else {
                    Log.i("Info", "Printing noteid from update condition " + noteId);
                    s = "NOTES_" + (noteId + 1);
                    dbHelper.updateNotes(content, date, s, username);
                }
                Intent intent = new Intent(noteWriting.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public void clickDelete(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void deleteButtonFunction(View view) {
        // 1. Initialize the sql
        // 2. Initialize SharedPreferences to get the logged in username
        // 3. Get the content of the notes using getText()
        // 4. Use Intents to go back to screen 2
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        EditText text = (EditText) findViewById(R.id.note);
        String content = text.getText().toString();
        String title = "NOTES_" + (noteId + 1);
        dbHelper.deleteNotes(content, title);

        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }


}
