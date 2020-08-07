package com.amirhosseinemadi.note.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import com.amirhosseinemadi.note.model.DbCreate;
import com.amirhosseinemadi.note.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class DbInteractor extends DbCreate {
    public DbInteractor(@Nullable Context context) {
        super(context);
    }

    public interface View
    {

    }

    public void insert(NoteModel noteModel)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title",noteModel.getTitle());
        cv.put("body",noteModel.getBody());
        db.insert(TBL_NAME,null,cv);

    }

    public List<NoteModel> select()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from "+TBL_NAME;
        Cursor cursor = db.rawQuery(query,null);
        List<NoteModel> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            NoteModel noteModel = new NoteModel();
            noteModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
            noteModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            noteModel.setBody(cursor.getString(cursor.getColumnIndex("body")));
            list.add(noteModel);
        }

        return list;
    }

    public void update(NoteModel noteModel)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("body",noteModel.getBody());
        db.update(TBL_NAME,cv,"id ="+noteModel.getId(),null);
    }

    public void delete(NoteModel noteModel)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TBL_NAME,"id="+noteModel.getId(),null);
    }


    public List<NoteModel> search(String param)
    {
        SQLiteDatabase db = getReadableDatabase();
        List<NoteModel> list = new ArrayList<>();
        String[] params = {param,param};
        Cursor cursor = db.rawQuery("select * from "+TBL_NAME+" where title like '%"+param+"%' or body like '%"+param+"%'",null);
        while (cursor.moveToNext())
        {
            NoteModel noteModel = new NoteModel();
            noteModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            noteModel.setBody(cursor.getString(cursor.getColumnIndex("body")));
            list.add(noteModel);
        }

        return list;
    }
}
