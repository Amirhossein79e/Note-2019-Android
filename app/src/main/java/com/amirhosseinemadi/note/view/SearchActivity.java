package com.amirhosseinemadi.note.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.amirhosseinemadi.note.R;
import com.amirhosseinemadi.note.model.DbInteractor;
import com.amirhosseinemadi.note.presentor.DbPresentor;

public class SearchActivity extends AppCompatActivity implements DbView {

    DbPresentor dbPresentor;
    SearchView searchView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_search);


        searchView.requestFocus();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            searchView.setFocusedByDefault(true);
        }

        searchView.setIconifiedByDefault(false);

        searchView.requestFocus();


        dbPresentor = new DbPresentor(SearchActivity.this,this,new DbInteractor(SearchActivity.this));



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dbPresentor.search(query,recyclerView);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dbPresentor.search(newText,recyclerView);
                return false;
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });

    }

    @Override
    public void onShow(AppCompatEditText editText, AppCompatTextView textView) {

    }
}
