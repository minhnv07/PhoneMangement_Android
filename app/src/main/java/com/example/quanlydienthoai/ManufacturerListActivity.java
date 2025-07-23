package com.example.quanlydienthoai;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlydienthoai.adapter.ManufacturerAdapter;
import com.example.quanlydienthoai.model.Manufacturer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManufacturerListActivity  extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btnAdd;
    ManufacturerAdapter adapter;
    List<Manufacturer> manufacturerList;
    DatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer_list);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerManufacturer);
        btnAdd = findViewById(R.id.btnAdd);

        loadData();

        btnAdd.setOnClickListener(v -> showAddDialog());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.manufacturer);
    }

    private void loadData() {
        manufacturerList = dbHelper.getAllManufacturers();
        adapter = new ManufacturerAdapter(this, manufacturerList, new ManufacturerAdapter.OnActionListener() {
            @Override
            public void onEdit(Manufacturer manufacturer) {
                showEditDialog(manufacturer);
            }

            @Override
            public void onDelete(Manufacturer manufacturer) {
                new AlertDialog.Builder(ManufacturerListActivity.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa hãng " + manufacturer.getName() + "?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            dbHelper.deleteManufacturer(manufacturer.getId());
                            loadData();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_manufacturer, null);
        EditText etName = view.findViewById(R.id.etName);
        EditText etDesc = view.findViewById(R.id.etDesc);

        new AlertDialog.Builder(this)
                .setTitle("Thêm hãng mới")
                .setView(view)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String desc = etDesc.getText().toString().trim();
                    if(!name.isEmpty()) {
                        Manufacturer manufacturer = new Manufacturer(0,name, desc);
                        dbHelper.addManufacturer(manufacturer);
                        loadData();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    public void showEditDialog(Manufacturer manufacturer) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_manufacturer, null);
        EditText etName = view.findViewById(R.id.etName);
        EditText etDesc = view.findViewById(R.id.etDesc);

        etName.setText(manufacturer.getName());
        etDesc.setText(manufacturer.getDescription());

        new AlertDialog.Builder(this)
                .setTitle("Sửa thông tin hãng sản xuất")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String desc = etDesc.getText().toString().trim();
                    if(!name.isEmpty()) {
                        manufacturer.setName(name);
                        manufacturer.setDescription(desc);
                        dbHelper.updateManufacturer(manufacturer);
                        loadData();
                    }
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

