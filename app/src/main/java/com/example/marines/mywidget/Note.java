package com.example.marines.mywidget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.marines.mywidget.DataBase.DataBaseManager;

import java.util.ArrayList;

/**
 * Created by Marines on 13/09/2016.
 */
public class Note {

    private static final String table = "note";
    private static DataBaseManager dataBaseManager;
    static SQLiteDatabase db;
    private Context context;
    public int id;
    public String note;
    public boolean isDone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Note(int id,String note, boolean isDone){
        this.id=id;
        this.note=note;
        this.isDone=isDone;
    }

    public Note(Context context, boolean isWrite) {
        dataBaseManager = new DataBaseManager(context);
        if(isWrite)
            db=dataBaseManager.openWriteableDB();
        else
            db=dataBaseManager.openReadableDB();
    }

    public ArrayList<Note> getNotes() {
        try {
            ArrayList<Note> notes = new ArrayList<Note>();
            Cursor rs = db.rawQuery("select * from "+table, null);
            while (rs.moveToNext()) {
                Note note = new Note(rs.getInt(0),rs.getString(1), rs.getInt(2) == 1);
                notes.add(note);
            }
            dataBaseManager.closeDB(db);
            return notes;

        } catch (Exception ex) {
            Log.e("getNotes ", ex.toString());
            return null;
        }
    }
    public Note getNote(int id) {
        try {
            Cursor rs = db.rawQuery("select * from "+table+" where id='"+ id +"'", null);
            while (rs.moveToFirst())
                return new Note(rs.getInt(0),rs.getString(1), rs.getInt(2) == 1);
            dataBaseManager.closeDB(db);
        } catch (Exception ex) {
            Log.e("getNote ", ex.toString());
            return null;
        }
        return null;
    }
    public boolean save(Note note){
        try {
            ContentValues values = new ContentValues();
            values.put("note",note.getNote());
            values.put("isDone", note.isDone?1:0);
            boolean result= db.insert(table, null, values) != 0;
            dataBaseManager.closeDB(db);
            return result;
        } catch (Exception ex) {
            Log.e("save ", ex.toString());
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            boolean result=db.delete(table, "id='"+ id +"'", null) != 0;
            dataBaseManager.closeDB(db);
            return  result;
        } catch (Exception ex) {
            Log.e("delete ", ex.toString());
            return false;
        }
    }

    public boolean update(Note note) {
        ContentValues valores = new ContentValues();
        try {
            valores.put("note", note.getNote());
            valores.put("isDone", note.isDone?1:0);
            boolean result= db.update(table, valores, "id='"+ note.getId() +"'",null)!= 0;
            dataBaseManager.closeDB(db);
            return  result;
        } catch (Exception ex) {
            Log.e("update ", ex.toString());
            return false;
        }
    }


}