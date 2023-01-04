package com.example.sharememories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RetrieveActivity extends AppCompatActivity {
    GridView gridView;
    ListItemAdapter listAdapter;
    ArrayList<ListItem> list_itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent secondIntent = getIntent();
        String groupId = secondIntent.getStringExtra("groupId");

        Button writeAction = (Button) findViewById(R.id.writeAction);
        writeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetrieveActivity.this, AddTitleActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });

        list_itemArrayList = new ArrayList<ListItem>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(groupId)
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list_itemArrayList.add(new ListItem(document.getId().toString(), document.get("date").toString(),
                                    Double.parseDouble(document.get("latitude").toString()), Double.parseDouble(document.get("longitude").toString()),
                                    document.get("location").toString(), document.get("content").toString()));
                        }
                    }
                }
            });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listAdapter = new ListItemAdapter(RetrieveActivity.this, list_itemArrayList);
                gridView = (GridView) findViewById(R.id.list);
                gridView.setAdapter(listAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                        final ListItem item = (ListItem) listAdapter.getItem(a_position);
                        //Toast.makeText(RetrieveActivity.this, item.getTitle() + " Click event", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RetrieveActivity.this, MainActivity.class);
                        intent.putExtra("lat", item.getLat());
                        intent.putExtra("lng", item.getLng());
                        intent.putExtra("location", item.getLoc());
                        intent.putExtra("memo", item.getMemo());
                        startActivity(intent);
                    }
                });
            }
        },1000);
    }


}