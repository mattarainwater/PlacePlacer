package com.example.matta.placeplacer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.matta.placeplacer.data.PlaceDataLayer;
import com.example.matta.placeplacer.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mGoogleMap;
    private View mHiddenPanel;
    private LatLng mCurrentLatLng;
    private String mGoogleId;
    private PlaceDataLayer mDl;
    private ArrayList<Place> savedMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
        mHiddenPanel = findViewById(R.id.hidden_panel);

        Intent intent = getIntent();
        mGoogleId = intent.getStringExtra("GOOGLE_ID");

        mDl = new PlaceDataLayer(getApplicationContext());
        savedMarkers = mDl.getPlaces(mGoogleId);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnMarkerClickListener(this);
        for (Place place : savedMarkers) {
            Marker marker = mGoogleMap.addMarker(getMarkerFromPlace(place));
            marker.setTag(place);
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        if(!isPanelShown()) {
            mCurrentLatLng = point;
            slideUpDown(null);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Place p = (Place) marker.getTag();
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My place: " + p.name);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, p.description);

        startActivity(Intent.createChooser(shareIntent, "Share!"));

        return true;
    }

    public void slideUpDown(View v) {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);
            clearForm();
            mHiddenPanel.startAnimation(bottomUp);
            mHiddenPanel.setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.latlong_view)).setText(mCurrentLatLng.toString());
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            mHiddenPanel.startAnimation(bottomDown);
            mHiddenPanel.setVisibility(View.GONE);
        }
    }


    public void save(View v) {
        String name = ((TextView) findViewById(R.id.edit_name)).getText().toString();
        String description = ((TextView) findViewById(R.id.edit_description)).getText().toString();
        double lat = mCurrentLatLng.latitude;
        double lng = mCurrentLatLng.longitude;
        String googleId = mGoogleId;
        Place place = new Place(name, description, lat, lng, googleId);

        Marker marker = mGoogleMap.addMarker(getMarkerFromPlace(place));
        marker.setTag(place);
        mDl.addPlace(place);
        savedMarkers.add(place);
        slideUpDown(v);
    }

    private boolean isPanelShown() {
        return mHiddenPanel.getVisibility() == View.VISIBLE;
    }

    private MarkerOptions getMarkerFromPlace(Place place) {
        return new MarkerOptions()
                .position(new LatLng(place.latitude, place.longitude))
                .title(place.name)
                .snippet(place.description);
    }

    private void clearForm()
    {
        ((TextView) findViewById(R.id.edit_name)).setText("");
        ((TextView) findViewById(R.id.edit_description)).setText("");
    }
}
