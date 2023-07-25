package com.example.shareprefrences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
   ArrayList<Note>notes;
Context context;
OnClick onClick;
    public myAdapter(Context c, ArrayList<Note> notes, OnClick onClick) {
      context=c;
      this.notes=notes;
      this.onClick=onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getTitle());
        holder.text.setText(notes.get(position).getNote());
        int pos=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.setClick(pos);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete item")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.notes.remove(pos);
                                MainActivity.myAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences=holder.itemView.getContext().getSharedPreferences("package com.example.shareprefrences", Context.MODE_PRIVATE);
                                Set<String> noteSet = convertNoteSetToStringSet(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes", noteSet).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;

            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.title);
            text=itemView.findViewById(R.id.note);
        }

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
