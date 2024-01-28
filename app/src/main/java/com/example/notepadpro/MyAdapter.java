package com.example.notepadpro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private RealmResults<Note> notesList;

    public MyAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleoutput.setText(note.getTitle());
        holder.descriptionoutput.setText(note.getDescription());

        String formattedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeoutput.setText(formattedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenu().add("DELETE");
                menu.getMenu().add("EDIT");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("DELETE")) {
                            //delete note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                        }
                        if(item.getTitle().equals("EDIT")){
                            //get note
                           Toast.makeText(context, "Edit Note Coming SOON!!!", Toast.LENGTH_SHORT).show();

                        }
                        return true;

                    }
                });
                menu.show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleoutput;
        TextView descriptionoutput;
        TextView timeoutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleoutput = itemView.findViewById(R.id.titleoutput);
            descriptionoutput = itemView.findViewById(R.id.descriptionoutput);
            timeoutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}
