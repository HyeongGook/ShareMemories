package com.example.sharememories;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    public LatLng mapMarkerPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent secondIntent = getIntent();
        mapMarkerPos = new LatLng(secondIntent.getDoubleExtra("lat", 0), secondIntent.getDoubleExtra("lng", 0));
        String location = secondIntent.getStringExtra("location");
        TextView location_view = (TextView) findViewById(R.id.locationView);
        location_view.setText(location.concat("에서의 기록"));
        String memo = secondIntent.getStringExtra("memo");
        TextView memo_view = (TextView) findViewById(R.id.showMemo);
        memo_view.setText(memo);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button goToMain = (Button) findViewById(R.id.goToMain);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.title("here");
        markerOptions1.position(mapMarkerPos);
        googleMap.addMarker(markerOptions1);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapMarkerPos, 16));
    }
}