package com.restassured.RestApiAutomation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductData {

    @JsonProperty("year")
    private int year;

    @JsonProperty("price")
    private double price;

    @JsonProperty("CPU model")
    private String CPU_model;

    @JsonProperty("Hard disk size")
    private String Hard_disk_size;

    @JsonProperty("color")
    private String color; // Optional field that appears in some entries

    // Default constructor
    public ProductData() {
    }

    // Constructor with all fields
    public ProductData(int year, double price, String CPU_model, String Hard_disk_size, String color) {
        this.year = year;
        this.price = price;
        this.CPU_model = CPU_model;
        this.Hard_disk_size = Hard_disk_size;
        this.color = color;
    }

    // Getters and setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCPU_model() {
        return CPU_model;
    }

    public void setCPU_model(String CPU_model) {
        this.CPU_model = CPU_model;
    }

    public String getHard_disk_size() {
        return Hard_disk_size;
    }

    public void setHard_disk_size(String Hard_disk_size) {
        this.Hard_disk_size = Hard_disk_size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "year=" + year +
                ", price=" + price +
                ", CPU_model='" + CPU_model + '\'' +
                ", Hard_disk_size='" + Hard_disk_size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}