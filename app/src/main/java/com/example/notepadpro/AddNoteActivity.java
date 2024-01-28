package com.example.notepadpro;

import android.os.Bundle;  // Make sure to import Bundle
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText titleinput = findViewById(R.id.titleinput);
        EditText descriptioninput = findViewById(R.id.descriptioninput);
        MaterialButton savebutton = findViewById(R.id.savebutton);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleinput.getText().toString();
                String description = descriptioninput.getText().toString();
                long createdTime = System.currentTimeMillis();

                realm.beginTransaction();
                Note note = realm.createObject(Note.class);
                note.setTitle(title);
                note.setDescription(description);
                note.setCreatedTime(createdTime);
                realm.commitTransaction();
                Toast.makeText(AddNoteActivity.this, "Note Added", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}
