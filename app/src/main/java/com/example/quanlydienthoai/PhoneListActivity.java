package com.example.quanlydienthoai;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlydienthoai.adapter.PhoneAdapter;
import com.example.quanlydienthoai.model.Manufacturer;
import com.example.quanlydienthoai.model.Phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PhoneListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhoneAdapter adapter;
    List<Phone> phones;
    Button btnAdd;
    Map<Integer, String> manufactoryMap;
    DatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_list);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerPhone);
        btnAdd = findViewById(R.id.btnAdd);

        loadData();

        btnAdd.setOnClickListener(v -> showAddDialog());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.phone);
    }

    private void loadData(){
        phones = dbHelper.getAllPhones();
        List<Manufacturer> manufacturers = dbHelper.getAllManufacturers();
        manufactoryMap = new HashMap<>();
        for (Manufacturer m : manufacturers){
            manufactoryMap.put(m.getId(), m.getName());
        }
        adapter = new PhoneAdapter(this, phones, manufactoryMap, new PhoneAdapter.OnActionListener() {
            @Override
            public void onEdit(Phone phone) {
                showEditDialog(phone);
            }

            @Override
            public void onDelete(Phone phone) {
                dbHelper.deletePhone(phone.getId());
                loadData();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void showAddDialog(){
        View view = getLayoutInflater().inflate(R.layout.dialog_phone, null);
        EditText etPhoneName = view.findViewById(R.id.etPhoneName);
        Spinner spinnerManufacturer = view.findViewById(R.id.spnManufacturer);
        EditText stScreenSize = view.findViewById(R.id.etScreenSize);
        EditText etRating = view.findViewById(R.id.etRating);

        List<Manufacturer> manufacturers = dbHelper.getAllManufacturers();
        List<String> manufacturerNames = new ArrayList<>();
        for (Manufacturer m : manufacturers){
            manufacturerNames.add(m.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, manufacturerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManufacturer.setAdapter(adapter);

        new AlertDialog.Builder(this)
                .setTitle("Thêm điện thoại")
                .setView(view)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String phoneName = etPhoneName.getText().toString().trim();
                    String screenSizeStr = stScreenSize.getText().toString().trim();
                    String rating = etRating.getText().toString().trim();
                    int manuPos = spinnerManufacturer.getSelectedItemPosition();

                    if (phoneName.isEmpty() || screenSizeStr.isEmpty() || manufacturers.isEmpty()) return;
                    int manufacturerId = manufacturers.get(manuPos).getId();
                    float screenSize = Float.parseFloat(screenSizeStr);

                    Phone phone = new Phone(0,phoneName, manufacturerId, screenSize, rating);
                    dbHelper.addPhone(phone);
                    loadData();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(Phone phone){
        View view = getLayoutInflater().inflate(R.layout.dialog_phone, null);
        EditText etPhoneName = view.findViewById(R.id.etPhoneName);
        Spinner spinnerManufacturer = view.findViewById(R.id.spnManufacturer);
        EditText stScreenSize = view.findViewById(R.id.etScreenSize);
        EditText etRating = view.findViewById(R.id.etRating);

        List<Manufacturer> manufacturers = dbHelper.getAllManufacturers();
        List<String> manufacturerNames = new ArrayList<>();
        for (Manufacturer m : manufacturers){
            manufacturerNames.add(m.getName());
            if (m.getId() == phone.getManufacturerId()){
                spinnerManufacturer.setSelection(manufacturerNames.indexOf(m.getName()));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, manufacturerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManufacturer.setAdapter(adapter);

        etPhoneName.setText(phone.getName());
        stScreenSize.setText(String.valueOf(phone.getScreenSize()));
        etRating.setText(phone.getRating());

        new AlertDialog.Builder(this)
                .setTitle("Sửa điện thoại")
                .setView(view)
                .setPositiveButton("Sửa", (dialog, which) -> {
                    String phoneName = etPhoneName.getText().toString().trim();
                    String screenSizeStr = stScreenSize.getText().toString().trim();
                    String rating = etRating.getText().toString().trim();
                    int manuPos = spinnerManufacturer.getSelectedItemPosition();

                    if (phoneName.isEmpty() || screenSizeStr.isEmpty() || manufacturers.isEmpty()) return;
                    int manufacturerId = manufacturers.get(manuPos).getId();
                    float screenSize = Float.parseFloat(screenSizeStr);

                    Phone newPhone = new Phone(phone.getId(), phoneName, manufacturerId, screenSize, rating);
                    dbHelper.updatePhone(newPhone);
                    loadData();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
