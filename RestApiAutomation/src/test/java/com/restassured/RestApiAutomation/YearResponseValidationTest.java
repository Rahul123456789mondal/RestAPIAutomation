package com.restassured.RestApiAutomation;

// Import REST Assured static methods for a fluent API
import static io.restassured.RestAssured.*;
// Import Hamcrest matchers for expressive assertions
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Demonstrates how to validate a specific field in the JSON response and the
 * response time.
 *
 * Endpoint: https://api.restful-api.dev/objects/7
 * Expected: The JSON body contains a field "year" with value 2019.
 * Also checks that the API responds within an acceptable time window.
 */
public class YearResponseValidationTest {

    @BeforeClass
    public void setup() {
        // Base URI for the RESTful‑API demo service
        baseURI = "https://api.restful-api.dev";
    }

    @Test
    public void validateYearAndResponseTime() {

        Response response = given()
                // No query or path parameters needed – endpoint is fixed
                .when()
                // GET request to /objects/7 – the full URL becomes
                // https://api.restful-api.dev/objects/7
                .get("/objects/7")
                .then()
                // Log the complete response for debugging purposes
                .log().all()
                // Expect HTTP 200 (OK) – the resource should exist
                .statusCode(200)
                // Validate that the response is JSON
                .contentType(ContentType.JSON)
                // Verify the JSON field "data.year" equals 2019
                // The JSONPath expression "data.year" accesses the nested field
                .body("data.year", equalTo(2019))
                .body("id", equalTo("7"))
                // Ensure the API responds quickly – less than 2 seconds (2000 ms)
                .time(lessThan(3000L))
                // Extract the response to use with JsonPath
                .extract().response();

        // 2. Use JsonPath to extract and show the JSON data
        JsonPath js = response.jsonPath();

        System.out.println("\n=== Extracted JSON Data via JsonPath ===");

        // Extracting standard string and integer values
        String id = js.getString("id");
        String name = js.getString("name");
        int year = js.getInt("data.year");
        float price = js.getFloat("data.price");

        // Note: Keys with spaces like "CPU model" must be wrapped in single quotes
        String cpuModel = js.getString("data.'CPU model'");
        String hardDiskSize = js.getString("data.'Hard disk size'");

        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
        System.out.println("CPU Model: " + cpuModel);
        System.out.println("Hard Disk Size: " + hardDiskSize);
    }

    @Test
    public void fetchObjectFromCollection() {
        given()
                // 1. Pass the Authentication Header
                .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")

                // 2. Define the Path Parameters matching the {braces} in the URL
                .pathParam("collectionName", "testproducts")
                .pathParam("id", "ff8081819d150699019d214dee9315c2")

                // Optional: Log the request to see exactly what is being sent
                .log().uri()
                .log().headers()
                .when()
                // 3. REST Assured will automatically inject "products" and "7" here
                .get("/collections/{collectionName}/objects/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo("ff8081819d150699019d214dee9315c2"))
                .body("name", equalTo("Apple MacBook Pro 16 Test"));
    }

    @Test
    public void createNewPostRequest() {
        // 1. Define the JSON Payload (Body) for the new object
        String jsonBody = "{\"name\":\"My Custom Laptop\",\"data\":{\"year\":2024,\"price\":1299.99,\"CPU model\":\"Intel Core i7\",\"Hard disk size\":\"512GB\"}}";

        given()
                // 1. Pass the Authentication Header
                .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")
                // 2. Define the Path Parameters matching the {braces} in the URL
                .pathParam("collectionName", "testproducts")
                // 3. Set the Content-Type header to tell the server we are sending JSON
                .contentType(ContentType.JSON)
                // 4. Attach the JSON body to the request
                .body(jsonBody)
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
                .body("name", equalTo("My Custom Laptop"));
    }

}
