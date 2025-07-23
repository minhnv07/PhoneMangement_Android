package com.example.quanlydienthoai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button btnManufacturer, btnPhone, btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.home);

        btnManufacturer = findViewById(R.id.btnManufacturer);
        btnPhone = findViewById(R.id.btnPhone);
        btnSearch= findViewById(R.id.btnSearch);

        btnManufacturer.setOnClickListener(v -> startActivity(new Intent(this, ManufacturerListActivity.class)));
        btnPhone.setOnClickListener(v -> startActivity(new Intent(this, PhoneListActivity.class)));
        btnSearch.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));

    }
}
