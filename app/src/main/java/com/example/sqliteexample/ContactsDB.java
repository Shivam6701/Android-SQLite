package com.example.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactsDB {
    public static final String KEY_ROWID ="_id";   //Add atleast one underscore in each name
    public static final String KEY_NAME = "person_name";
    public static final String KEY_PNO = "_pno";

    private final String DATABASE_NAME ="ContactsDB";
    private final String DATABASE_TABLE ="ContactsTable";
    private final int DATABASE_VERSION =1;

    private DBHelper ourHelper;
    private final Context ourcontext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB(Context context)
    {
        ourcontext =context;
    }
    private class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
              CREATE TABLE ContactsTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    person_name TEXT NOT NULL,
                    _pno TEXT NOT NULL);
            */

            String sqlCode= "CREATE TABLE "+DATABASE_TABLE+" ("+
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_NAME +" TEXT NOT NULL, "+
                    KEY_PNO +" TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }
    public ContactsDB open() throws SQLException
    {
        ourHelper= new DBHelper(ourcontext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }
    public long createEntry(String name,String num)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_PNO,num);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }
    public String getData()
    {
        String [] columns = new String[] {KEY_ROWID, KEY_NAME, KEY_PNO};
        Cursor  c =ourDatabase.query(DATABASE_TABLE, columns,null,null,null,null,null);

        String result  = "";
        int iRowId = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iPno = c.getColumnIndex(KEY_PNO);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result = result+c.getString(iRowId) + ": " +c.getString(iName)+" "+c.getString(iPno)+"\n";
        }
        c.close();
        return result;
    }
    public long deleteEntry(String rowId)
    {
        return ourDatabase.delete(DATABASE_TABLE,KEY_ROWID+"=?",new String[]{rowId});
    }
    public long updateEntry(String rowId, String name,String pno)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_PNO,pno);
        return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID+"=?",new String[]{rowId});
    }

}
