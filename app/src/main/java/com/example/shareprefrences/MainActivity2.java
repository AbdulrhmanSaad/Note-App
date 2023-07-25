package com.example.shareprefrences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {
EditText editText1,editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        Button save;
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText1.getText().toString();
                String text = editText2.getText().toString();
                Note n = new Note(title, text);
                MainActivity.notes.add(n);
                MainActivity.myAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("package com.example.shareprefrences", Context.MODE_PRIVATE);
                Set<String> noteSet = convertNoteSetToStringSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", noteSet).apply();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private Set<String> convertNoteSetToStringSet(ArrayList<Note> noteSet) {
        Set<String> stringSet = new HashSet<>();
        for (Note note : noteSet) {
            String noteString = note.getTitle() + "||" + note.getNote();
            stringSet.add(noteString);
        }
        return stringSet;
    }
}