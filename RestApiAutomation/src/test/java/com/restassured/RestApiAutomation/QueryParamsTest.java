package com.restassured.RestApiAutomation;

// Import all REST Assured methods statically - allows direct use of given(), when(), then(), etc.
import static io.restassured.RestAssured.*;
// Import Hamcrest matchers for response body assertions
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QueryParamsTest {

    // Setup base URI before running any test method in this class
    @BeforeClass
    public void setup() {
        // BaseURI is the common prefix for all API endpoints
        baseURI = "https://jobs.postmanatwork.com";
    }

    // Test to verify API works with a single query parameter
    // Query params are appended as ?key=value to the URL
    @Test
    public void getJobsByLocation() {

        given()
            // Add a single query parameter - filters jobs by country
            // Final URL becomes: https://jobs.postmanatwork.com/jobs?country=US
            .queryParam("country", "US")
        .when()
            // Send HTTP GET request to the /jobs endpoint
            .get("/jobs")
        .then()
            // Assert that the API returns HTTP 200 (OK) status code
            .statusCode(200)
            // Validate that response content type is JSON
            .contentType(ContentType.JSON)
            // Assert that the response body contains a non-null 'data' field
            .body("data", notNullValue())
            // Log the complete response for debugging and verification
            .log().all();
    }

    // Test to verify API works with multiple query parameters
    // Multiple query params are chained as ?key1=value1&key2=value2
    @Test
    public void getJobsByLocationAndCountry() {

        given()
            // First query parameter - filters jobs by location (city name)
            .queryParam("location", "San Francisco")
            // Second query parameter - filters jobs by country code
            // Both params combined: /jobs?location=San Francisco&country=US
            .queryParam("country", "US")
        .when()
            // Send HTTP GET request to the /jobs endpoint with query params
            .get("/jobs")
        .then()
            // Assert that the API returns HTTP 200 (OK) status code
            .statusCode(200)
            // Validate that response content type is JSON
            .contentType(ContentType.JSON)
            // Assert that the response body contains a non-null 'data' field
            .body("data", notNullValue())
            // Log the complete response for debugging and verification
            .log().all();
    }

}
