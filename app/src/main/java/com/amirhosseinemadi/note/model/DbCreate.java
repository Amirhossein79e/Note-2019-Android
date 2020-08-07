package com.amirhosseinemadi.note.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbCreate extends SQLiteOpenHelper {

    public final static String DB_NAME = "notes";
    public final static int DB_VERSION = 2;
    public final static String TBL_NAME = "notes";

    public DbCreate(@Nullable Context context) {
        super(context , DB_NAME ,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "create table if not exists "+TBL_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT , title TEXT(40) , body TEXT )";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
