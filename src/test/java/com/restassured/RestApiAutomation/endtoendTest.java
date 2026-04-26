package com.restassured.RestApiAutomation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.restassured.RestApiAutomation.pojo.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class endtoendTest {

    String id = "";
    List<Product> fetchedProducts = null;

    @BeforeClass
    public void setup() {
        // Base URI for the RESTful‑API demo service
        RestAssured.baseURI = "https://api.restful-api.dev";
    }

    // Create Method to Call the API POST request
    @Test
    public void createPostRequest() {
        // Read the JSON file from the resources folder
        File jsonFile = new File("src/test/resources/request_data.json");

        Product createdProduct = given()
                // 1. Pass the Authentication Header
                .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")
                // 2. Define the Path Parameters matching the {braces} in the URL
                .pathParam("collectionName", "testproducts")
                // 3. Set the Content-Type header to tell the server we are sending JSON
                .contentType(ContentType.JSON)
                // 4. Attach the JSON body to the request
                .body(jsonFile)
                // Optional: Log the request to see exactly what is being sent
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                // 5. POST request to the /objects endpoint
                .post("/collections/{collectionName}/objects")
                .then()
                .log().all()
                .statusCode(200) // 200 OK is the standard response for a successful POST
                .body("name", equalTo("New Data April 24")) // Updated to match the JSON file
                // Extract the response as a Product POJO
                .extract().as(Product.class);

        // Extract the ID from the response POJO
        id = createdProduct.getId();
        System.out.println("Created Object ID: " + id);
        System.out.println("Created Product Name: " + createdProduct.getName());
        if (createdProduct.getData() != null) {
            System.out.println("Created Product Year: " + createdProduct.getData().getYear());
        }
    }

    @Test
    public void validateProductByID() {
        // Validate the product by ID using the ID captured from createPostRequest
        Product product = given()
                // 1. Pass the Authentication Header
                .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")
                // 2. Define the Path Parameters matching the {braces} in the URL
                .pathParam("collectionName", "testproducts")
                .pathParam("id", id) // Using the ID captured from createPostRequest
                // Optional: Log the request to see exactly what is being sent
                .log().uri()
                .log().headers()
                .when()
                // 3. GET request to the /collections/{collectionName}/objects/{id} endpoint
                .get("/collections/{collectionName}/objects/{id}")
                .then()
                .log().all()
                .statusCode(200)
                // Validate that the returned ID matches the one we captured
                .body("id", equalTo(id))
                // Validate that the name matches what we sent in the POST request
                .body("name", equalTo("New Data April 24"))
                // Extract the response as a Product POJO for further use
                .extract().as(Product.class);

        // Display product information using POJO
        System.out.println("Validated product: " + product.getName());
        if (product.getData() != null) {
            System.out.println("Product year: " + product.getData().getYear());
            System.out.println("Product price: $" + product.getData().getPrice());
            System.out.println("Product CPU: " + product.getData().getCPU_model());
            System.out.println("Product Hard disk size: " + product.getData().getHard_disk_size());
        }
    }

    @Test
    public void fetchAllProducts() {
        // Fetch all products from the collection and store the response as POJO
        Product[] productsArray = given()
                // 1. Pass the Authentication Header
                .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")
                // 2. Define the Path Parameters matching the {braces} in the URL
                .pathParam("collectionName", "testproducts")
                // Optional: Log the request to see exactly what is being sent
                .log().uri()
                .log().headers()
                .when()
                // 3. GET request to the /collections/{collectionName}/objects endpoint
                .get("/collections/{collectionName}/objects")
                .then()
                .log().all()
                .statusCode(200)
                // Extract the response as Product array using POJO classes
                .extract().as(Product[].class);

        // Convert array to List and store in class variable
        fetchedProducts = Arrays.asList(productsArray);

        // Store the response in a class variable for use by other methods
        System.out.println("Fetched all products. Response stored as POJO objects in List.");
        System.out.println("Total products fetched: " + fetchedProducts.size());
        // Display information about the first few products
        if (!fetchedProducts.isEmpty()) {
            System.out.println("First product: " + fetchedProducts.get(0).getName());
            if (fetchedProducts.get(0).getData() != null) {
                System.out.println("First product year: " + fetchedProducts.get(0).getData().getYear());
                System.out.println("First product price: $" +
                        fetchedProducts.get(0).getData().getPrice());
            }
        }

    }

    @Test
    public void updateProductByID() {
        // Update the product by ID using PUT request
        // Read the JSON file from the resources folder
        File jsonFile = new File("src/test/resources/put_request.json");

        Product updatedProduct = given()
                // 1. Pass the Authentication Header
                .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")
                // 2. Define the Path Parameters matching the {braces} in the URL
                .pathParam("collectionName", "testproducts")
                .pathParam("id", id) // Using the ID captured from createPostRequest
                // 3. Set the Content-Type header to tell the server we are sending JSON
                .contentType(ContentType.JSON)
                // 4. Attach the JSON body to the request
                .body(jsonFile)
                // Optional: Log the request to see exactly what is being sent
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                // 5. PUT request to the /collections/{collectionName}/objects/{id} endpoint
                .put("/collections/{collectionName}/objects/{id}")
                .then()
                .log().all()
                .statusCode(200) // 200 OK is the standard response for a successful PUT
                // Extract the response as a Product POJO
                .extract().as(Product.class);

        // Display updated product information
        System.out.println("Updated product with ID: " + id);
        System.out.println("Updated Product Name: " + updatedProduct.getName());
        if (updatedProduct.getData() != null) {
            System.out.println("Updated Product Year: " + updatedProduct.getData().getYear());
            System.out.println("Updated Product Price: $" + updatedProduct.getData().getPrice());
            System.out.println("Updated Product CPU Model: " + updatedProduct.getData().getCPU_model());
            System.out.println("Updated Product Hard Disk Size: " + updatedProduct.getData().getHard_disk_size());
        }
    }

}

/*
 * 
 * // This is the another step to send the request body
 * 31 - // String jsonBody = "{\"name\":\"My MSI
 * 32 - //
 * Laptop\",\"data\":{\"year\":2024,\"price\":1299.99,\"CPU model\":\"Intel Core
 * 33 - // i5\",\"Hard disk size\":\"512GB\"}}";
 * 34 -
 * 35 - // 1. Create the inner nested JSON object ("data")
 * 36 - Map<String, Object> productData = new HashMap<>();
 * 37 - productData.put("year", 2019);
 * 38 - productData.put("price", 22234);
 * 39 - productData.put("CPU model", "Intel Core i9");
 * 40 - productData.put("Hard disk size", "1 TB");
 * 41 -
 * 42 - // 2. Create the main JSON object and attach the inner map
 * 43 - Map<String, Object> requestPayload = new HashMap<>();
 * 44 - requestPayload.put("name", "Arka New Test");
 * 45 - requestPayload.put("data", productData); // Nesting happens here
 * 46 -
 * 47 - // 3. Read the JSON file from the resources folder
 * 
 * 
 */