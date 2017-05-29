package com.example.marines.mywidget.DataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

	private static DbHelper sInstance;
	private static final String DATABASE_NAME = "DBNote";
	private static final int DATABASE_VERSION = 1;

	public static synchronized DbHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new DbHelper(context.getApplicationContext());
		}
		return sInstance;
	}



	private DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DataBaseManager.noteTable);
		db.execSQL(DataBaseManager.configureTable);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS noteTable");
		db.execSQL("DROP TABLE IF EXISTS configure");
		onCreate(db);
	}
}
