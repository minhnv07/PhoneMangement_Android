package com.example.quanlydienthoai.model;

public class Phone {
    private int id;
    private String name;
    private int manufacturerId;
    private float screenSize;
    private String rating;

    public Phone(int id, String name, int manufacturerId, float screenSize, String rating) {
        this.id = id;
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.screenSize = screenSize;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
