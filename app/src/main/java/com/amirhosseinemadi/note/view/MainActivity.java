package com.amirhosseinemadi.note.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.amirhosseinemadi.note.R;
import com.amirhosseinemadi.note.model.DbCreate;
import com.amirhosseinemadi.note.model.DbInteractor;
import com.amirhosseinemadi.note.presentor.DbPresentor;
import com.google.android.material.navigation.NavigationView;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity implements DbView {

    Toolbar toolbar;
    DbCreate dbCreate;
    DbPresentor dbPresentor;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RelativeLayout empty;
    CircleButton addButton;
    RelativeLayout relativeLayout;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.main);


        addButton = findViewById(R.id.btn_add_note);

        navigationView = findViewById(R.id.navigation_view);

        drawerLayout = findViewById(R.id.drawer);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        dbCreate = new DbCreate(MainActivity.this);

        recyclerView = findViewById(R.id.recycler_main);

        dbPresentor = new DbPresentor(MainActivity.this,this,new DbInteractor(MainActivity.this));

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.contact :
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:amirhossein79.e@gmail.com"));
                        startActivity(intent);
                        break;

                    case R.id.about :
                        Intent intent1 = new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.exit :
                        finish();
                        break;
                }
                return false;
            }
        });

        empty = findViewById(R.id.empty_layout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbPresentor.select(MainActivity.this,recyclerView);
        if (DbPresentor.count == 0)
        {
            empty.setVisibility(View.VISIBLE);
        }
        else
        {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.search)
        {
            Intent intent = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onShow(AppCompatEditText editText, AppCompatTextView textView) {

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.exit);
        alert.setMessage(getString(R.string.areYouWantExit));
        alert.setNegativeButton(R.string.yes,null);
        alert.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alert.show();
    }
}
