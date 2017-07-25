package com.example.matta.placeplacer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matta.placeplacer.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matta on 7/23/2017.
 */

public class PlaceDataLayer {
    private SQLiteDatabase db;

    public PlaceDataLayer(Context context) {
        PlaceDbHelper dbHelper = new PlaceDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void addPlace(Place place)
    {
        ContentValues values = new ContentValues();
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_NAME, place.name);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION, place.description);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_LATITUDE, place.latitude);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_LONGITUDE, place.longitude);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_GOOGLEID, place.googleId);

        db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Place> getPlaces(String googleId)
    {
        String[] projection = {
                PlaceContract.PlaceEntry._ID,
                PlaceContract.PlaceEntry.COLUMN_NAME_NAME,
                PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION,
                PlaceContract.PlaceEntry.COLUMN_NAME_LATITUDE,
                PlaceContract.PlaceEntry.COLUMN_NAME_LONGITUDE,
                PlaceContract.PlaceEntry.COLUMN_NAME_GOOGLEID,
        };

        String selection = PlaceContract.PlaceEntry.COLUMN_NAME_GOOGLEID + " = ?";
        String[] selectionArgs = { googleId };

        Cursor cursor = db.query(
                PlaceContract.PlaceEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        ArrayList<Place> places = new ArrayList<Place>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION));
            double latitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_LATITUDE));
            double longitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_LONGITUDE));
            places.add(new Place(name, description, latitude, longitude, googleId));
        }
        cursor.close();

        return places;
    }
}
