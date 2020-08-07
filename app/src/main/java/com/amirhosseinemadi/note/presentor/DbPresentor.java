package com.amirhosseinemadi.note.presentor;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amirhosseinemadi.note.model.DbInteractor;
import com.amirhosseinemadi.note.model.NoteAdapter;
import com.amirhosseinemadi.note.model.NoteModel;
import com.amirhosseinemadi.note.view.DbView;

import java.util.List;

public class DbPresentor implements DbInteractor.View {

    DbView dbView;
    DbInteractor dbInteractor;
    Context context;
    public static int count;

    public DbPresentor(Context context ,DbView dbView, DbInteractor dbInteractor)
    {
        this.context = context;
        this.dbView = dbView;
        this.dbInteractor = new DbInteractor(context);
    }

    public void insert(NoteModel noteModel)
    {
        dbInteractor.insert(noteModel);
    }

    public void select(Context context,RecyclerView recyclerView)
    {
        List<NoteModel> list = dbInteractor.select();
        NoteAdapter noteAdapter = new NoteAdapter(context,list);
        recyclerView.setAdapter(noteAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        count = noteAdapter.getItemCount();
    }

    public void update(NoteModel noteModel)
    {
        dbInteractor.update(noteModel);
    }

    public void delete(NoteModel noteModel)
    {
        dbInteractor.delete(noteModel);
    }

    public void search(String param,RecyclerView recyclerView)
    {
        List<NoteModel> list = dbInteractor.search(param);
        NoteAdapter noteAdapter = new NoteAdapter(context,list);
        recyclerView.setAdapter(noteAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
