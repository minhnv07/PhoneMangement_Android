package com.example.quanlydienthoai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.quanlydienthoai.model.Manufacturer;
import com.example.quanlydienthoai.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "phones.db";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Manufacturer(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT)");
        db.execSQL("CREATE TABLE Phone(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "manufacturerId INTEGER," +
                "screenSize REAL," +
                "rating TEXT," +
                "FOREIGN KEY(manufacturerId) REFERENCES Manufacturer(id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Phone");
        db.execSQL("DROP TABLE IF EXISTS Manufacturer");
        onCreate(db);
    }

    public long addManufacturer(Manufacturer manufacturer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", manufacturer.getName());
        values.put("description", manufacturer.getDescription());
        return db.insert("Manufacturer", null, values);
    }

    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Manufacturer", null);
        while (cursor.moveToNext()) {
            int id;
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            list.add(new Manufacturer(id, name, description));
        }
        cursor.close();
        return list;
    }

    public int updateManufacturer(Manufacturer manufacturer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", manufacturer.getName());
        values.put("description", manufacturer.getDescription());
        return db.update("Manufacturer", values, "id=?", new String[]{String.valueOf(manufacturer.getId())});
    }
    public int deleteManufacturer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Manufacturer", "id=?", new String[]{String.valueOf(id)});
    }

    public long addPhone(Phone phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", phone.getName());
        values.put("manufacturerId", phone.getManufacturerId());
        values.put("screenSize", phone.getScreenSize());
        values.put("rating", phone.getRating());
        return db.insert("Phone", null, values);
    }

    public List<Phone> getAllPhones() {
        List<Phone> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Phone", null);
        while (cursor.moveToNext()) {
            int id;
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int manufacturerId = cursor.getInt(cursor.getColumnIndexOrThrow("manufacturerId"));
            float screenSize = cursor.getFloat(cursor.getColumnIndexOrThrow("screenSize"));
            String rating = cursor.getString(cursor.getColumnIndexOrThrow("rating"));
            list.add(new Phone(id, name, manufacturerId, screenSize, rating));
        }
        cursor.close();
        return list;
    }
    public int updatePhone(Phone phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", phone.getName());
        values.put("manufacturerId", phone.getManufacturerId());
        values.put("screenSize", phone.getScreenSize());
        values.put("rating", phone.getRating());
        return db.update("Phone", values, "id=?", new String[]{String.valueOf(phone.getId())});
    }

    public int deletePhone(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Phone", "id=?", new String[]{String.valueOf(id)});
    }

    public List<Phone> searchPhones(Integer manufacturerId, Boolean isGreater6) {
        List<Phone> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder sql = new StringBuilder("SELECT * FROM Phone WHERE 1=1");
        List<String> args = new ArrayList<>();

        if (manufacturerId != null && manufacturerId != -1) {
            sql.append(" AND manufacturerId=?");
            args.add(String.valueOf(manufacturerId));
        }
        if(isGreater6 != null){
            if(isGreater6){
                sql.append(" AND screenSize > 6");
            }
            else{
                sql.append(" AND screenSize <= 6");
            }
        }

        Cursor cursor = db.rawQuery(sql.toString(), args.toArray(new String[0]));
        while (cursor.moveToNext()) {
            int id;
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int manufacturerId1 = cursor.getInt(cursor.getColumnIndexOrThrow("manufacturerId"));
            float screenSize = cursor.getFloat(cursor.getColumnIndexOrThrow("screenSize"));
            String rating = cursor.getString(cursor.getColumnIndexOrThrow("rating"));
            list.add(new Phone(id, name, manufacturerId1, screenSize, rating));
        }
        cursor.close();
        return list;
    }
}

