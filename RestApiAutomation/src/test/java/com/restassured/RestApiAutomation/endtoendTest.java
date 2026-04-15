package com.restassured.RestApiAutomation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class endtoendTest {

    String id = "";

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

        String response = given()
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
                .body("name", equalTo("Arka Neo test")) // Updated to match the JSON file
                .extract().response().asString();

        // Extract the ID from the response
        JsonPath js = new JsonPath(response);
        id = js.getString("id");
        System.out.println("Created Object ID: " + id);
    }

    @Test
    public void validateProductByID() {
        // Validate the product by ID using the ID captured from createPostRequest
        given()
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
                .body("name", equalTo("Arka Neo test")); // Updated to match the JSON file
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