package com.example.sharememories;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

public class AddMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private boolean modeSelect;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        modeSelect = false;

        Button addMarker = (Button) findViewById(R.id.addMarker);
        addMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modeSelect) {
                    addMarker.setText("마커  추가모드");
                    modeSelect = false;
                } else {
                    addMarker.setText("추가모드  해제");
                    modeSelect = true;
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                if (modeSelect) {
                    MarkerOptions mOptions = new MarkerOptions();
                    //mOptions.title("마커 좌표");
                    Double latitude = point.latitude; // 위도
                    Double longitude = point.longitude; // 경도
                    mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                    mOptions.position(new LatLng(latitude, longitude));
                    googleMap.addMarker(mOptions);
                }
            }
        });
        LatLng location = new LatLng(37.571433, 127.026007);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("우리집");
        markerOptions.snippet("지금당장 가고싶은 우리집입니다.");
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
    }
}