package com.example.marines.mywidget.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DataBaseManager {

    private DbHelper helper;
    public static SQLiteDatabase sqLiteDatabase;
    public DataBaseManager(Context context) {
        this.helper = DbHelper.getInstance(context);
    }

    public SQLiteDatabase openWriteableDB() {
        if(sqLiteDatabase == null ||  !sqLiteDatabase.isOpen() ) {
            sqLiteDatabase = this.helper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }
    public SQLiteDatabase openReadableDB() {
        if(sqLiteDatabase == null ||  !sqLiteDatabase.isOpen() ) {
            sqLiteDatabase = this.helper.getReadableDatabase();
        }
        return sqLiteDatabase;
    }
    public void closeDB(SQLiteDatabase db){
        if(db!=null){
            db.close();
        }
    }

    public static String noteTable = "CREATE TABLE note(id INTEGER NOT NULL PRIMARY KEY,"
            + "note TEXT(100) NOT NULL,"
            + "isDone INTEGER NOT NULL)";
    public static String configureTable = "CREATE TABLE configure(id INTEGER NOT NULL PRIMARY KEY,"
            + "idWidget INTEGER NOT NULL,"
            + "color INTEGER NOT NULL,"
            + "texColor INTEGER NOT NULL,"
            + "title TEXT(250) NOT NULL)";

}
