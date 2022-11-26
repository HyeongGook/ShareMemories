package com.example.sharememories;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RetrieveActivity extends AppCompatActivity {

    String[] locations = {"서울", "다낭", "대만", "마카오", "홍콩", "블라디보스톡"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Spinner spinner = findViewById(R.id.selectLocation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}