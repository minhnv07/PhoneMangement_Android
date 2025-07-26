package com.example.quanlydienthoai;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quanlydienthoai.adapter.PhoneAdapter;
import com.example.quanlydienthoai.model.Manufacturer;
import com.example.quanlydienthoai.model.Phone;
import java.util.*;

public class SearchActivity extends AppCompatActivity {
    Spinner spnManufacturer;
    RadioGroup rgScreenSize;
    Button btnSearch;
    RecyclerView recyclerSearch;
    DatabaseHelper dbHelper;
    List<Manufacturer> manufacturers;
    Map<Integer, String> manufacturerMap;
    PhoneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbHelper = new DatabaseHelper(this);
        spnManufacturer = findViewById(R.id.spnManufacturer);
        rgScreenSize = findViewById(R.id.rgScreenSize);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerSearch = findViewById(R.id.recyclerSearch);

        manufacturers = dbHelper.getAllManufacturers();
        List<String> manuNames = new ArrayList<>();
        manuNames.add("Tất cả");
        for (Manufacturer m : manufacturers) manuNames.add(m.getName());
        ArrayAdapter<String> manuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, manuNames);
        manuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnManufacturer.setAdapter(manuAdapter);

        manufacturerMap = new HashMap<>();
        for (Manufacturer m : manufacturers) manufacturerMap.put(m.getId(), m.getName());

        adapter = new PhoneAdapter(this, new ArrayList<>(), manufacturerMap, null);
        recyclerSearch.setAdapter(adapter);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(this));

        btnSearch.setOnClickListener(v -> doSearch());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search);
    }

    private void doSearch() {
        Integer manuId = null;
        int pos = spnManufacturer.getSelectedItemPosition();
        if (pos > 0) manuId = manufacturers.get(pos - 1).getId();

        Boolean greaterThan6 = null;
        int checked = rgScreenSize.getCheckedRadioButtonId();
        if (checked == R.id.rbGreater6) greaterThan6 = true;
        else if (checked == R.id.rbLessOrEqual6) greaterThan6 = false;

        List<Phone> resultList = dbHelper.searchPhones(manuId, greaterThan6);
        adapter = new PhoneAdapter(this, resultList, manufacturerMap, null);
        recyclerSearch.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
