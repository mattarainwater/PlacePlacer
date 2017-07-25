package com.example.matta.placeplacer.data;

import android.provider.BaseColumns;

/**
 * Created by matta on 7/23/2017.
 */

public final class PlaceContract {
    private PlaceContract() {}

    public static class PlaceEntry implements BaseColumns {
        public static final String TABLE_NAME = "place";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_GOOGLEID = "googleid";
    }
}
