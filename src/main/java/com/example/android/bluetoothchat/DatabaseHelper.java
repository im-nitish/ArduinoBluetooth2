package com.example.android.bluetoothchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DBVERSION = 1;
    private static final String DBNAME = "Record.db";
    private static final String TNAME = "Records";

    public static final String CID = "_id";
    public static final String CVAL = "Value";
    public static final String CDnT = "DatenTime";
    SQLiteDatabase db;

    private static final String CREATEDB = "Create table Records(_id integer , Value text, DatenTime text);";


    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATEDB);
            this.db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TNAME);
    }

    public void insertdata(Records r)
    {
        db = this.getWritableDatabase();
        ContentValues  val = new ContentValues();
        val.put(CID, r.getId());
        val.put(CVAL, r.getValue());
        val.put(CDnT, r.getDatenTime());

        db.insert(TNAME, null, val);
        db.close();
    }

    public Cursor getRecord()
    {
        db = this.getReadableDatabase();

        String query = "Select _id, Value, DatenTime from "+TNAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

}
