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
import java.util.Locale;

import at.markushi.ui.CircleButton;

public class AddActivity extends AppCompatActivity implements DbView {

    AppCompatEditText title,body;
    Toolbar toolbar;
    DbPresentor dbPresentor;
    CircleButton circleButton;
    Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        locale = new Locale("fa");

        dbPresentor = new DbPresentor(AddActivity.this,this,new DbInteractor(AddActivity.this));

        toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.title_add);
        body = findViewById(R.id.body_add);

        circleButton = findViewById(R.id.btn_voice_add);

        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fa");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speak));
                startActivityForResult(intent,1);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.save)
        {
            String title1 = title.getText().toString();
            String body1 = body.getText().toString();
            NoteModel noteModel = new NoteModel();
            noteModel.setTitle(title1);
            noteModel.setBody(body1);
            dbPresentor.insert(noteModel);
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShow(AppCompatEditText editText, AppCompatTextView textView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                body.append(arrayList.get(0)+" ");
            }
        }
    }
}
