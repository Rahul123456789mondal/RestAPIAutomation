# REST API Automation Framework

[![Java](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/)
[![REST Assured](https://img.shields.io/badge/REST_Assured-5.4.0-orange)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.9.0-red)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.0.0_M5-green)](https://maven.apache.org/)

A comprehensive REST API testing framework built with REST Assured for automated testing of RESTful web services.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Endpoints Under Test](#api-endpoints-under-test)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Test Examples & Architecture](#test-examples--architecture)
- [Contributing](#contributing)
- [License](#license)

## 📖 Overview

This project is a robust REST API testing framework designed to automate the testing of RESTful web services. It leverages the power of REST Assured, a Java-based DSL (Domain Specific Language) for easy testing of REST services, combined with TestNG for test execution and reporting.

The framework demonstrates various API testing techniques including:
- Query parameters
- Path parameters
- Response validation (status codes, headers, body content)
- Response time validation
- JSON schema validation
- End-to-end API workflows

## ✨ Features

- **Comprehensive API Testing**: Covers GET, POST, and PUT HTTP methods.
- **Parameter Testing**: Both query and path parameter validation.
- **Response Validation**: Status codes, headers, body content, and response time.
- **Detailed Logging**: Configurable logging for debugging and verification using `.log().all()`, `.log().body()`, and `.log().ifValidationFails()`.
- **Modular Design**: Well-organized test classes for different functionalities with Base URI configuration in `@BeforeClass`.
- **POJO Integration**: Advanced JSON serialization and deserialization using Jackson and Plain Old Java Objects (POJOs) for type-safe data access.

## 🔧 Technologies Used

- **Java 21**: Programming language
- **REST Assured 5.4.0**: REST API testing library
- **TestNG 7.9.0**: Testing framework
- **Hamcrest 3.0**: Matcher objects for expressive assertions
- **Jackson**: JSON serialization/deserialization library
- **Maven 3.0.0-M5**: Build automation tool
- **VS Code**: Development environment

## 🌐 API Endpoints Under Test

1. **Postman Jobs API** (`https://jobs.postmanatwork.com`):
   - `/jobs` - Get list of jobs with query parameters
   - `/jobs/{jobId}` - Get specific job by ID

2. **RESTful API Dev** (`https://api.restful-api.dev`):
   - `/objects/7` - Get specific object by ID
   - `/collections/{collectionName}/objects/{id}` - Get object from collection
   - `/collections/{collectionName}/objects` - Create new objects

## 🗂️ Project Structure

```
RestApiAutomation/
├── src/
│   ├── main/java/com/restassured/RestApiAutomation/
│   │   ├── GetRequestExample.java          # Basic GET request example
│   │   └── pojo/                          # POJO classes for JSON mapping
│   │       ├── Product.java               # Product entity POJO
│   │       ├── ProductData.java           # Product data nested POJO
│   │       └── ProductList.java           # Product list container POJO
│   └── test/
│       ├── java/com/restassured/RestApiAutomation/
│       │   ├── QueryParamsTest.java        # Query parameter testing
│       │   ├── PathParamsTest.java         # Path parameter testing
│       │   ├── ValidateResponseDemo.java   # Comprehensive response validation
│       │   ├── YearResponseValidationTest.java # Specific field validation
│       │   ├── endtoendTest.java           # Complete end-to-end API workflows
│       │   └── POJOExampleTest.java        # Usage of POJO classes for JSON mapping
│       └── resources/
│           ├── request_data.json           # Sample JSON payload data for POST requests
│           ├── put_request.json            # Sample JSON payload data for PUT requests
│           └── all_response.json           # Sample response data
├── pom.xml                                 # Maven configuration
├── CLAUDE.md                               # Project guidance for Claude Code
└── POJOCLASS.md                            # Detailed POJO implementation guide
```

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher installed
- Maven 3.0 or higher installed
- Git installed
- An IDE (VS Code recommended)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Rahul123456789mondal/RestAPIAutomation.git
   ```

2. Navigate to the project directory:
   ```bash
   cd RestAPIAutomation
   ```

3. Compile the project:
   ```bash
   mvn compile
   ```

## ▶️ Running Tests

The project includes TestNG XML configuration files that organize tests into logical suites.

### Build and Test Commands

```bash
# Run all tests
mvn test

# Run tests with verbose output
mvn test -Dsurefire.printSummary=true

# Run a specific test class
mvn test -Dtest=QueryParamsTest

# Run a specific test method
mvn test -Dtest=QueryParamsTest#getJobsByLocation

# Run tests using TestNG suite XML
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Clean and rebuild
mvn clean install
```

## 🧪 Test Examples & Architecture

### Query & Path Parameter Testing
- **Query Parameters**: Uses `.queryParam("key", "value")` to test endpoints like `/jobs?country=US` (`QueryParamsTest`).
- **Path Parameters**: Uses `.pathParam("paramName", "value")` to test endpoints with `{paramName}` in URL like `/jobs/{jobId}` (`PathParamsTest`).

### Response Validation
Validates status codes, headers, response time, and body content (`ValidateResponseDemo` and `YearResponseValidationTest`).
- Status codes: `.statusCode(200)`
- Content types: `.contentType(ContentType.JSON)`
- Body content: `.body("json.path", matcher)`
- Response time: `.time(lessThan(3000L))`

### End-to-End Workflows (`endtoendTest.java`)
Demonstrates a complete CRUD cycle using POJO classes:

1. **Create (POST)**: `createPostRequest()`
   - Sends a POST request using data from `request_data.json`.
   - Captures the generated `id` for subsequent steps.

2. **Read & Validate (GET)**: `validateProductByID()`
   - Validates the newly created product using the captured `id`.

3. **Fetch & Filter (GET)**: `fetchAllProducts()`
   - Fetches all objects, stores them as a List of POJOs, and filters to find the specific item matching the captured ID, storing it in a dedicated `UpdateProducts` list.

4. **Update (PUT) via JSON File**: `updateProductByID()`
   - Updates the created product using data from `put_request.json` and authenticates via headers.

5. **Update (PUT) via POJO Body**: `updateProductWithPojoBody()`
   - Demonstrates updating a product by dynamically creating `ProductData` and `Product` POJO objects programmatically and sending them as the request body.

### POJO Integration
Detailed in `POJOCLASS.md`, POJOs are used for JSON serialization and deserialization. Tests like `POJOExampleTest` showcase how to structure and map complex nested JSON structures to Java objects.

### Learning & Backup Scenarios (`Backup_Code.java`)
The framework includes a `Backup_Code.java` file designed for educational purposes to demonstrate advanced data handling:
- **`fetchAllProductsToHashMap()`**: Fetches all products and stores them in a `HashMap` (Key: ID, Value: Product POJO) for efficient lookup.
- **`updateRandomProductFromMap()`**: Randomly selects an item from the map using Java Streams, dynamically modifies its payload, and performs a PUT request to update the specific record.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📬 Contact

Rahul Mondal - [@Rahul123456789mondal](https://github.com/Rahul123456789mondal)

Project Link: [https://github.com/Rahul123456789mondal/RestAPIAutomation](https://github.com/Rahul123456789mondal/RestAPIAutomation)