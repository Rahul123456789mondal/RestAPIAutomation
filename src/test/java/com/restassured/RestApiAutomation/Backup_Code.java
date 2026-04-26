package com.restassured.RestApiAutomation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.restassured.RestApiAutomation.pojo.Product;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class Backup_Code {

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

    @Test
    public void checkAllProduct() {

        fetchedProducts = given()
                // 1. Authentication Header
                .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")

                // 2. Path Parameter
                .pathParam("collectionName", "testproducts")

                // Logging (optional but good)
                .log().uri()
                .log().headers()

                .when()
                .get("/collections/{collectionName}/objects")

                .then()
                .log().all()
                .statusCode(200)

                // ✅ IMPORTANT CHANGE HERE
                .extract()
                .jsonPath()
                .getList("", Product.class);

        System.out.println("Total products fetched: " + fetchedProducts.size());

        for (Product product : fetchedProducts) {
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());

            System.out.println("Year: " + product.getData().getYear());
            System.out.println("Price: " + product.getData().getPrice());
            System.out.println("CPU: " + product.getData().getCPU_model());
            System.out.println("Disk: " + product.getData().getHard_disk_size());

            System.out.println("----------------------------");
        }

    }

}
