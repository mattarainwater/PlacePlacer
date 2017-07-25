package com.example.matta.placeplacer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matta on 7/23/2017.
 */

public class PlaceDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlaceContract.PlaceEntry.TABLE_NAME + " (" +
                    PlaceContract.PlaceEntry._ID + " INTEGER PRIMARY KEY," +
                    PlaceContract.PlaceEntry.COLUMN_NAME_NAME + " TEXT," +
                    PlaceContract.PlaceEntry.COLUMN_NAME_LATITUDE + " DOUBLE," +
                    PlaceContract.PlaceEntry.COLUMN_NAME_LONGITUDE + " DOUBLE," +
                    PlaceContract.PlaceEntry.COLUMN_NAME_GOOGLEID + " TEXT," +
                    PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlaceContract.PlaceEntry.TABLE_NAME;

    public PlaceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
