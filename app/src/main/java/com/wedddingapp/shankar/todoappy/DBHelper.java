package com.wedddingapp.shankar.todoappy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shan on 12/20/2017.
 */

public class DBHelper extends SQLiteOpenHelper
{

    //Creating final variables.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoym";  //Database name


    //Constructor.
    public DBHelper(Context context )
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //onCreate method.
    public void onCreate(SQLiteDatabase db)
    {
        //Query
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + SEntry.TABLE  + "("
                + SEntry.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + SEntry.KEY_TITLE + " TEXT, "
                + SEntry.KEY_DESCRIPTION+" TEXT, "
                + SEntry.KEY_DATE +" TEXT, "
                + SEntry.KEY_STATUS + " INTEGER )";

        // we are Executing query by db.
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    //Method to upgrade the database, But here we left it empty because we don't need to upgrade the db in this app.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
