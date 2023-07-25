package com.example.shareprefrences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static SharedPreferences sharedPreferences;
    static ArrayList<Note> notes=new ArrayList<>();
    static myAdapter myAdapter;
    OnClick onClick;
    FloatingActionButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.add);
        //notes.add(new Note("test","this is a test note"));
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("package com.example.shareprefrences", Context.MODE_PRIVATE);
        Set<String> stringSet = sharedPreferences.getStringSet("notes", new HashSet<>());
        HashSet<Note> ss=convertStringSetToNoteSet(stringSet);
        notes=new ArrayList<>(ss);
        onClick=new OnClick() {
            @Override
            public void setClick(int pos) {
                Intent intent=new Intent(MainActivity.this,MainActivity3.class);
                intent.putExtra("id",pos);
                startActivity(intent);
            }
        };
        RecyclerView recyclerView=findViewById(R.id.recycle);
        myAdapter=new myAdapter(this,notes,onClick);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    private HashSet<Note> convertStringSetToNoteSet(Set<String> stringSet) {
        HashSet<Note> noteSet = new HashSet<>();
        for (String noteString : stringSet) {
            String[] parts = noteString.split("\\|\\|");
            if (parts.length == 2) {
                String title = parts[0];
                String text = parts[1];
                Note note = new Note(title, text);
                noteSet.add(note);
            }
        }
        return noteSet;
    }
}