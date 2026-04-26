package com.restassured.RestApiAutomation;

// Import all REST Assured methods statically - allows direct use of given(), when(), then(), etc.
import static io.restassured.RestAssured.*;
// Import Hamcrest matchers for response body assertions
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

// Renamed from "PathaParamsTest" to "PathParamsTest" (fixed typo - missing 'r')
// Path params are dynamic URL segments like /jobs/{id} where {id} gets replaced
public class PathParamsTest {

    // Setup method runs once before any test method in this class
    // @BeforeClass ensures baseURI is set before tests execute
    @BeforeClass
    public void setup() {
        // BaseURI is the common prefix for all API endpoints
        // Setting it here avoids repetition in each test method
        baseURI = "https://jobs.postmanatwork.com";
    }

    // Test to verify fetching a specific job by its unique ID
    // Path params replace placeholders in the URL path: /jobs/{jobId}
    @Test
    public void getJobById() {

        // Given = Setup test preconditions (headers, params, body)
        given()
            // Define a path parameter - jobId will replace {jobId} in the URL
            // Final URL becomes: https://jobs.postmanatwork.com/jobs/TB18MDx9h5tGkrZMUFycs
            .pathParam("jobId", "TB18MDx9h5tGkrZMUFycs")
        .when()
            // Send HTTP GET request to /jobs/{jobId} endpoint
            // The {jobId} placeholder is replaced with the value defined above
            .get("/jobs/{jobId}")
        .then()
            // Assert that the API returns HTTP 200 (OK) status code
            // 200 means the request succeeded and job was found
            .statusCode(200)
            // Validate that response content type is JSON
            // Ensures API returns data in expected JSON format
            .contentType(ContentType.JSON)
            // Assert that the response body contains a non-null 'data' field
            // Verifies the API actually returned job data, not empty response
            .body("data", notNullValue())
            // Log the complete response for debugging and verification
            // Prints response headers, body, and status to console
            .log().all();
    }

}
