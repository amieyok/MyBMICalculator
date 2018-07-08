package com.mobile.mybmicalculator;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersonalBmi extends SQLiteOpenHelper {

    public static final String dbName = "dbPersonalBmi";
    public static final String tblName = "BMI";
    public static final String colName = "name";
    public static final String colWeight = "weight";
    public static final String colHeight = "height";
    public static final String colStatus = "status";

    public PersonalBmi(Context context)
    {
        super(context,dbName,null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ tblName +" ("+ colName +" VARCHAR, " + colWeight +" DOUBLE, " + colHeight + " DOUBLE,"+ colStatus +" VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ tblName +" ");
        onCreate(db);
    }

    public void fnExecuteSql(String strSql, Context appContext)
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(strSql);
        } catch (Exception e)
        {
            Log.d("unable to run query", "error!");
        }
    }


    public int fnTotalRow()
    {
        int intRow;
        SQLiteDatabase db = this.getReadableDatabase();
        intRow = (int) DatabaseUtils.queryNumEntries(db, tblName);

        return intRow;
    }
}
