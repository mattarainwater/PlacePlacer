package com.example.matta.placeplacer.model;

import java.io.Serializable;

/**
 * Created by matta on 7/23/2017.
 */

public class Place implements Serializable {
    public Place(
            String name,
            String description,
            double latitude,
            double longitude,
            String googleId)
    {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.googleId = googleId;
    }

    public String name;
    public String description;
    public double latitude;
    public double longitude;
    public String googleId;
}
