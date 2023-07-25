package com.example.shareprefrences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        EditText editText=findViewById(R.id.editText);
        EditText editText1=findViewById(R.id.editText2);
        Intent intent=getIntent();
        int pos=intent.getIntExtra("id",-1);
        editText.setText(MainActivity.notes.get(pos).getTitle());
        editText1.setText(MainActivity.notes.get(pos).getNote());
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Note n=new Note(editText.getText().toString(),String.valueOf(charSequence));
                MainActivity.notes.set(pos,n);
                MainActivity.myAdapter.notifyDataSetChanged();
               SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("package com.example.shareprefrences", Context.MODE_PRIVATE);
                Set<String> noteSet = convertNoteSetToStringSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", noteSet).apply();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Note n=new Note(String.valueOf(charSequence),editText1.getText().toString());
                MainActivity.notes.set(pos,n);
                MainActivity.myAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("package com.example.shareprefrences", Context.MODE_PRIVATE);
                Set<String> noteSet = convertNoteSetToStringSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", noteSet).apply();
            }
            @Override
            public void afterTextChanged(Editable editable) {
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