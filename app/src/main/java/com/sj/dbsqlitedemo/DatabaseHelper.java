package com.sj.dbsqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="StudentDB.db";
    public static final String TABLE_NAME = "student_table";
    public static final String col_1 = "ID";
    public static final String col_2 = "FirstName";
    public static final String col_3 = "LastName";
    public static final String col_4 = "Marks";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FirstName Text, LastName Text, Marks Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);

    }

    // insert DATA in DB
    public boolean insertData(String firstName, String lastName, String marks){

        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();

        contentValues.put(col_2,firstName);
        contentValues.put(col_3,lastName);
        contentValues.put(col_4,marks);

        myDb.insert("student_table",null,contentValues);
        return true;
    }

    // Retrieve data from Database
    public Cursor getAllData(){

        SQLiteDatabase myDb = this.getReadableDatabase();

        Cursor cursor = myDb.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }

    // Update data in Databse
    public boolean updateDatabase(String id , String name, String surname, String marks){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col_1,id);
        contentValues.put(col_2,name);
        contentValues.put(col_3,surname);
        contentValues.put(col_4,marks);

        myDB.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }

    // Delete data from DB

    public Integer DeleteDataInDB(String id){
        SQLiteDatabase myDB = this.getWritableDatabase();

        return myDB.delete(TABLE_NAME,"ID=?",new String[]{id});
    }
}
