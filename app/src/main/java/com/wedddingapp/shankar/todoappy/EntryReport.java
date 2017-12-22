package com.wedddingapp.shankar.todoappy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shan on 12/21/2017.
 */

public class EntryReport {

    private DBHelper dbHelper;


    public EntryReport(Context context)

    {

        dbHelper = new DBHelper(context);

    }



    public int insert(SEntry SEntry)
    {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues(); //we use content values to write the values into the database

        //Putting values in the ContentValue Object.
        values.put(SEntry.KEY_TITLE, SEntry.title);
        values.put(SEntry.KEY_DESCRIPTION, SEntry.description);
        values.put(SEntry.KEY_DATE, SEntry.dueDate);
        values.put(SEntry.KEY_STATUS, SEntry.status);

        // Inserting Row
        long student_Id = db.insert(SEntry.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;   //returning ID.
    }

    //Method to delete entry from database.
    public void delete(int entry_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //query to delete from the database based on  id
        db.delete(SEntry.TABLE, SEntry.KEY_ID + "= ?", new String[] { String.valueOf(entry_Id) });
        db.close(); // Closing database connection
    }

    //Method to update the Entries of the database.
    public void update(SEntry SEntry) {

        //Creating reference of writable db.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Creating object of ContentValues.
        ContentValues values = new ContentValues();

        //Putting values in the contentValues object.

        //if you want to update any field say title  or any field if you want to update it with new value in respective fields
        values.put(SEntry.KEY_TITLE, SEntry.title);
        values.put(SEntry.KEY_DESCRIPTION, SEntry.description);
        values.put(SEntry.KEY_DATE, SEntry.dueDate);
        values.put(SEntry.KEY_STATUS, SEntry.status);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(SEntry.TABLE, values, SEntry.KEY_ID + "= ?", new String[] { String.valueOf(SEntry.entry_ID) });
        db.close(); // Closing database connection
    }


    //Method to retrive all Entries of DB.
    public ArrayList<SEntry> getEntryList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Select query to retrive elements.
        String selectQuery =  "SELECT  " +
                SEntry.KEY_ID + "," +
                SEntry.KEY_TITLE + "," +
                SEntry.KEY_DESCRIPTION+","+
                SEntry.KEY_DATE+","+
                SEntry.KEY_STATUS+
                " FROM " + SEntry.TABLE;

        //Student student = new Student();
        ArrayList<SEntry> SEntryList = new ArrayList<SEntry>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all the rows and adding to list

        //Creating SEntry objects and putting them into ArrayList.
        if (cursor.moveToFirst()) {
            do {
                SEntry SEntry =new SEntry();
                SEntry.entry_ID=cursor.getInt(cursor.getColumnIndex(SEntry.KEY_ID));
                SEntry.title=cursor.getString(cursor.getColumnIndex(SEntry.KEY_TITLE));
                SEntry.description=cursor.getString(cursor.getColumnIndex(SEntry.KEY_DESCRIPTION));
                SEntry.dueDate=cursor.getString(cursor.getColumnIndex(SEntry.KEY_DATE));
                SEntry.status=cursor.getInt(cursor.getColumnIndex(SEntry.KEY_STATUS));
                SEntryList.add(SEntry);

            } while (cursor.moveToNext());
        }

        //Sorting the ArrayList by sort() method in Collections class.
        //We already compared the objects in the containing class only in compareTo() method.
        Collections.sort(SEntryList);

        //Closing the cursor and db objects.
        cursor.close();
        db.close();
        return SEntryList;    //returning ArrayList.

    }

    //Method to get SEntry object by ID.
    public SEntry getStudentById(int Id){
        //Getting reference of Readable DB.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Select query.
        String selectQuery =  "SELECT  " +
                SEntry.KEY_ID + "," +
                SEntry.KEY_TITLE + "," +
                SEntry.KEY_DESCRIPTION+","+
                SEntry.KEY_DATE+","+
                SEntry.KEY_STATUS+
                " FROM " + SEntry.TABLE
                + " WHERE " +
                SEntry.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        SEntry SEntry =new SEntry();       //Creating object of SEntry class.

        //Creating cursor for selectQuery.
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        //Getting the values.
        if (cursor.moveToFirst()) {
            do {

                SEntry.entry_ID=cursor.getInt(cursor.getColumnIndex(SEntry.KEY_ID));
                SEntry.title=cursor.getString(cursor.getColumnIndex(SEntry.KEY_TITLE));
                SEntry.description=cursor.getString(cursor.getColumnIndex(SEntry.KEY_DESCRIPTION));
                SEntry.dueDate=cursor.getString(cursor.getColumnIndex(SEntry.KEY_DATE));
                SEntry.status=cursor.getInt(cursor.getColumnIndex(SEntry.KEY_STATUS));

            } while (cursor.moveToNext());
        }

        //closing objects of db and cursor.
        cursor.close();
        db.close();
        return SEntry;   //returning SEntry.
    }

    //Method to retrive Entries whose status is 1 from DB.
    public ArrayList<SEntry> getCompletedEntryList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Select query to retrive elements.
        String selectQuery =  "SELECT  " +
                SEntry.KEY_ID + "," +
                SEntry.KEY_TITLE + "," +
                SEntry.KEY_DESCRIPTION+","+
                SEntry.KEY_DATE+","+
                SEntry.KEY_STATUS+
                " FROM " + SEntry.TABLE;

        //Student student = new Student();
        ArrayList<SEntry> SEntryList = new ArrayList<SEntry>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        //Creating SEntry objects and putting them into ArrayList.
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getInt(cursor.getColumnIndex(SEntry.KEY_STATUS))==1) {
                    SEntry SEntry = new SEntry();
                    SEntry.entry_ID = cursor.getInt(cursor.getColumnIndex(SEntry.KEY_ID));
                    SEntry.title = cursor.getString(cursor.getColumnIndex(SEntry.KEY_TITLE));
                    SEntry.description = cursor.getString(cursor.getColumnIndex(SEntry.KEY_DESCRIPTION));
                    SEntry.dueDate = cursor.getString(cursor.getColumnIndex(SEntry.KEY_DATE));
                    SEntry.status = cursor.getInt(cursor.getColumnIndex(SEntry.KEY_STATUS));
                    SEntryList.add(SEntry);
                }

            } while (cursor.moveToNext());
        }

        //Sorting the ArrayList by sort() method in Collections class.
        //We already compared the objects in the containing class only in compareTo() method.
        Collections.sort(SEntryList);

        //Closing the cursor and db objects.
        cursor.close();
        db.close();
        return SEntryList;   //returning ArrayList.

    }


}



















