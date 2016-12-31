package com.example.android;

/**
 * Created by aman on 24/12/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.bluetoothchat.Records;

public class DatabaseClass {

    private static final int DBVERSION = 1;
    private static final String DBNAME = "Record.db";
    private static final String DATABASE_TABLE = "Records";

    public static final String CID = "_id";
    public static final String CVAL = "Value";
    public static final String CDnT = "DatenTime";

    private static final String CREATEDB = "Create table Records(_id integer , Value text, DatenTime text);";
    private static final String CREATE_DB = "Create table if not exists Records(_id integer , Value text, DatenTime text);";

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATEDB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DatabaseClass(Context c){
        ourContext = c;
    }

    public DatabaseClass open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        ourDatabase.execSQL(CREATE_DB);
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long insertdata(Records r)
    {
        ContentValues val = new ContentValues();
        val.put(CID, r.getId());
        val.put(CVAL, r.getValue());
        val.put(CDnT, r.getDatenTime());

        return ourDatabase.insert(DATABASE_TABLE, null, val);

    }

    public Cursor getRecord()
    {
        String query = "Select _id, Value, DatenTime from "+DATABASE_TABLE;
        Cursor cursor = ourDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

    public void eraseData(){

        String query = "Delete from " + DATABASE_TABLE;
        ourDatabase.execSQL(query);

    }

    //AG: To check Whether database is empty or not.
    public int countRows(){

        String count = "SELECT count(*) FROM "+ DATABASE_TABLE;
        Cursor mcursor = ourDatabase.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        return icount;
    }

}
