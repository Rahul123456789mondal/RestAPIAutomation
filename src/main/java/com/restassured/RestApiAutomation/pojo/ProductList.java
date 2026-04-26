package com.restassured.RestApiAutomation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductList {
    @JsonProperty("products")
    private List<Product> products;

    // Default constructor
    public ProductList() {
    }

    // Constructor with products list
    public ProductList(List<Product> products) {
        this.products = products;
    }

    // Getter and setter
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "products=" + products +
                '}';
    }
}