package com.restassured.RestApiAutomation;

// Import all REST Assured methods statically - allows direct use of given(), when(), then(), etc.
import static io.restassured.RestAssured.*;
// Import Hamcrest matchers for response body assertions
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

// This class demonstrates comprehensive response validation techniques
// Shows how to validate status codes, headers, body content, and response time
public class ValidateResponseDemo {

    // Setup method runs once before any test method in this class
    // @BeforeClass ensures baseURI is set before tests execute
    @BeforeClass
    public void setup() {
        // BaseURI is the common prefix for all API endpoints
        // Setting it here avoids repetition in each test method
        baseURI = "https://jobs.postmanatwork.com";
    }

    // Test demonstrating complete response validation
    // Validates status code, content type, response time, and body content
    @Test
    public void validateCompleteResponse() {

        given()
            // Add query parameter to filter jobs by country
            // This narrows down the results to US-based jobs only
            .queryParam("country", "US")
        .when()
            // Send HTTP GET request to the /jobs endpoint
            .get("/jobs")
        .then()
            // Assert that the API returns HTTP 200 (OK) status code
            // 200 indicates the request was successful
            .statusCode(200)
            // Validate that response content type is JSON
            // Ensures API returns data in the expected format
            .contentType(ContentType.JSON)
            // Assert that the response body contains a non-null 'data' field
            // Verifies the API actually returned job data, not an empty response
            .body("data", notNullValue())
            // Assert that 'data' array has at least 1 element
            // Ensures there are job results returned, not empty array
            .body("data", hasSize(greaterThan(0)))
            // Validate response time is less than 2 seconds (2000 milliseconds)
            // Performance check - ensures API responds within acceptable time
            .time(lessThan(2000L))
            // Log the complete response for debugging and verification
            // Prints response headers, body, and status to console
            .log().all();
    }

    // Test demonstrating how to validate specific JSON fields
    // Uses JSON path expressions to target nested elements
    @Test
    public void validateSpecificFields() {

        given()
            // Define path parameter - jobId replaces {jobId} in URL
            // Fetches a specific job by its unique identifier
            .pathParam("jobId", "TB18MDx9h5tGkrZMUFycs")
        .when()
            // Send GET request to /jobs/{jobId} endpoint
            // Path param is substituted into the URL
            .get("/jobs/{jobId}")
        .then()
            // Assert HTTP 200 (OK) status code
            .statusCode(200)
            // Validate response is in JSON format
            .contentType(ContentType.JSON)
            // Validate specific field exists and is not null
            // 'data.id' accesses the id field inside the data object
            .body("data.id", notNullValue())
            // Validate 'data.title' field exists and is a string
            // Ensures the job has a title property
            .body("data.title", notNullValue())
            // Validate 'data.company' field exists and is not null
            // Ensures the job has a company property
            .body("data.company", notNullValue())
            // Log only the response body (not headers or status)
            // Cleaner output when you only need to see the data
            .log().body();
    }

    // Test demonstrating header validation
    // Validates response headers returned by the API
    @Test
    public void validateResponseHeaders() {

        given()
            // Add query parameter to filter by country
            .queryParam("country", "US")
        .when()
            // Send GET request to /jobs endpoint
            .get("/jobs")
        .then()
            // Assert HTTP 200 (OK) status code
            .statusCode(200)
            // Validate Content-Type header contains "application/json"
            // Ensures server is sending JSON response
            .header("Content-Type", containsString("application/json"))
            // Validate response has a non-empty body
            .body("data", notNullValue())
            // Log all response details for verification
            .log().all();
    }

    // Test demonstrating conditional logging on failure
    // Only logs response when validation fails - useful for debugging
    @Test
    public void validateWithConditionalLogging() {

        given()
            // Add query parameter for filtering
            .queryParam("country", "US")
        .when()
            // Send GET request to /jobs endpoint
            .get("/jobs")
        .then()
            // Assert HTTP 200 (OK) status code
            .statusCode(200)
            // Validate response body has data field
            .body("data", notNullValue())
            // Only log response if any assertion above fails
            // Reduces console output during successful test runs
            // Helpful for CI/CD pipelines to see failures clearly
            .log().ifValidationFails();
    }

}
