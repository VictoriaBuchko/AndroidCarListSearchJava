package com.example.myapplicationcar.model;


import java.io.Serializable;

public class Car implements Serializable {
    private int imageResource;
    private String brand;
    private String model;
    private int year;
    private String description;
    private int cost;

    public Car(int imageResource, String brand, String model, int year, String description, int cost) {
        this.imageResource = imageResource;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.description = description;
        this.cost = cost;
    }

    public int getImageResource() { return imageResource; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getDescription(){ return description; }
    public int getCost(){ return cost; }
}
