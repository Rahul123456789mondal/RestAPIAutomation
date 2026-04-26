package com.restassured.RestApiAutomation;

import com.restassured.RestApiAutomation.pojo.Product;
import com.restassured.RestApiAutomation.pojo.ProductData;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.testng.Assert.*;

public class POJOExampleTest {

    @Test
    public void testPOJOCreation() {
        // Create a ProductData object
        ProductData productData = new ProductData();
        productData.setYear(2026);
        productData.setPrice(1849.99);
        productData.setCPU_model("Intel Core i3");
        productData.setHard_disk_size("500 GB");

        // Create a Product object
        Product product = new Product();
        product.setId("test-id-123");
        product.setName("Test Product");
        product.setData(productData);
        System.out.println(product);

        // Verify the objects were created correctly
        assertEquals(product.getId(), "test-id-123");
        assertEquals(product.getName(), "Test Product");
        assertEquals(product.getData().getYear(), 2026);
        assertEquals(product.getData().getPrice(), 1849.99);
        assertEquals(product.getData().getCPU_model(), "Intel Core i3");
        assertEquals(product.getData().getHard_disk_size(), "500 GB");
    }

    @Test
    public void testJSONToPOJO() throws IOException {
        // This test demonstrates how to deserialize the all_response.json file to POJOs
        // Note: This is a simplified example. In practice, you would use ObjectMapper
        // to read the entire array and map it to Product[] or List<Product>

        ObjectMapper mapper = new ObjectMapper();

        // Example of how you could read the JSON file
        Product[] products = mapper.readValue(new File("src/test/resources/all_response.json"), Product[].class);

        // For demonstration, we'll just show the concept
        System.out.println(
                "POJO mapping example - JSON to Product objects can be done with ObjectMapper" + products.toString());
    }
}