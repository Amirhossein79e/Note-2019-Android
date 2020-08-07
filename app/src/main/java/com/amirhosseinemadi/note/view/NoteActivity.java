package com.amirhosseinemadi.note.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amirhosseinemadi.note.R;
import com.amirhosseinemadi.note.model.DbInteractor;
import com.amirhosseinemadi.note.presentor.DbPresentor;
import com.amirhosseinemadi.note.model.NoteModel;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class NoteActivity extends AppCompatActivity implements DbView {

    Toolbar toolbar;
    Bundle bundle;
    AppCompatTextView title;
    AppCompatEditText body;
    NoteModel noteModel;
    DbPresentor dbPresentor;
    CircleButton circleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        bundle = getIntent().getExtras();
        noteModel = bundle.getParcelable("noteModel");
        toolbar = findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        dbPresentor = new DbPresentor(NoteActivity.this,this,new DbInteractor(NoteActivity.this));

        circleButton = findViewById(R.id.btn_voice_note);

        title = findViewById(R.id.title_note);
        body = findViewById(R.id.body_note);
        onShow(body,title);


        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fa");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speak));
                startActivityForResult(intent,1);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.update)
        {
            String body11 = body.getText().toString();
            noteModel.setBody(body11);
            dbPresentor.update(noteModel);
            Toast.makeText(this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
            finish();
        }else
        if (item.getItemId() == R.id.delete)
        {
            dbPresentor.delete(noteModel);
            Toast.makeText(this, getString(R.string.deleted), Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShow(AppCompatEditText editText, AppCompatTextView textView) {
        if (noteModel != null)
        {
            textView.setText(noteModel.getTitle());
            editText.setText(noteModel.getBody());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                body.append(arrayList.get(0)+" ");
            }
        }
    }
}
