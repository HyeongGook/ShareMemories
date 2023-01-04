package com.example.sharememories;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private boolean modeSelect;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    public MarkerItem item;
    private int markerCount;
    private int nowMarker;
    private EditText memo;
    private Button addImage;
    private ImageView addImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent secondIntent = getIntent();
        String groupId = secondIntent.getStringExtra("groupId");
        String title = secondIntent.getStringExtra("title");
        String et_Date = secondIntent.getStringExtra("et_Date");
        String et_Loc = secondIntent.getStringExtra("et_Loc");

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        modeSelect = false;
        markerCount = 0;
        nowMarker = 0;
        memo = (EditText) findViewById(R.id.memo);

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

        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setContent(memo.getText().toString());

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference newCard = db.collection(groupId);
                Map<String, Object> noteTitle = new HashMap<>();
                noteTitle.put("date", et_Date);
                noteTitle.put("location", et_Loc);
                noteTitle.put("latitude", item.getMarkerLatLng().latitude);
                noteTitle.put("longitude", item.getMarkerLatLng().longitude);
                noteTitle.put("content", memo.getText().toString());
                newCard.document(title).set(noteTitle);

                Intent intent = new Intent(AddMarkerActivity.this, RetrieveActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                if (modeSelect && markerCount == 0) {
                    markerCount = 1;
                    Double latitude = point.latitude; // 위도
                    Double longitude = point.longitude; // 경도
                    item = new MarkerItem(new LatLng(latitude, longitude),"");
                    MarkerOptions mOptions = new MarkerOptions();
                    mOptions.title("here");
                    mOptions.position(new LatLng(latitude, longitude));
                    googleMap.addMarker(mOptions);
                }
            }
        });
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.571202, 126.976727), 15));
    }
}