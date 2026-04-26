package com.restassured.RestApiAutomation;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetRequestExample {

        public static void main(String[] args) {

                // Base URI
                RestAssured.baseURI = "https://jobs.postmanatwork.com";

                // Perform GET request
                Response response = RestAssured
                                .given()
                                .when()
                                .get("/jobs");

                // Print Status Code
                System.out.println("Status Code: " + response.getStatusCode());

                // Print Response Body
                System.out.println("Response Body:");
                System.out.println(response.getBody().asString());

                // Print Headers
                System.out.println("Headers:");
                System.out.println(response.getHeaders());

        }

}
