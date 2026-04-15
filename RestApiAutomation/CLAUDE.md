# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a REST API automation project using REST Assured framework for testing APIs. The project focuses on validating different aspects of REST APIs including:
- Query parameters
- Path parameters
- Response validation (status codes, headers, body content)
- Response time validation
- End-to-end API workflows

## Project Structure

```
RestApiAutomation/
├── src/
│   ├── main/java/com/restassured/RestApiAutomation/
│   │   └── GetRequestExample.java          # Basic GET request example
│   └── test/
│       ├── java/com/restassured/RestApiAutomation/
│       │   ├── QueryParamsTest.java        # Query parameter testing
│       │   ├── PathParamsTest.java         # Path parameter testing
│       │   ├── ValidateResponseDemo.java   # Comprehensive response validation
│       │   ├── YearResponseValidationTest.java # Specific field validation
│       │   └── endtoendTest.java           # End-to-end API workflow
│       └── resources/
│           └── request_data.json           # Sample JSON payload data
├── pom.xml                                 # Maven configuration
└── .vscode/                               # VS Code configuration
    ├── settings.json
    └── launch.json
```

## Common Commands

### Build and Test Commands

```bash
# Install dependencies and compile
mvn clean compile

# Run all tests
mvn test

# Run tests with verbose output
mvn test -Dsurefire.printSummary=true

# Run a specific test class
mvn test -Dtest=QueryParamsTest

# Run a specific test method
mvn test -Dtest=QueryParamsTest#getJobsByLocation

# Clean and rebuild
mvn clean install
```

### Development Workflow

1. **Adding new test cases**: Create new methods annotated with `@Test` in existing test classes
2. **Adding new API endpoints**: Create new test methods following the existing patterns
3. **Modifying request payloads**: Update the JSON structures in test methods or the request_data.json file
4. **Adding new validations**: Use Hamcrest matchers like `equalTo()`, `notNullValue()`, `greaterThan()` etc.

## Code Architecture

### Key Components

1. **Test Classes**:
   - `QueryParamsTest`: Tests API endpoints with query parameters (?key=value)
   - `PathParamsTest`: Tests API endpoints with path parameters (/resource/{id})
   - `ValidateResponseDemo`: Comprehensive response validation techniques
   - `YearResponseValidationTest`: Specific field validation and response time checks
   - `endtoendTest`: Complete end-to-end API workflows including POST requests
   - `GetRequestExample`: Basic standalone example for manual execution

2. **Frameworks Used**:
   - REST Assured: Primary API testing framework
   - TestNG: Test execution framework
   - Hamcrest: Assertion library for expressive validations

3. **Key Patterns**:
   - BDD-style syntax with `given()`, `when()`, `then()` methods
   - Static imports for cleaner code (`import static io.restassured.RestAssured.*`)
   - Base URI configuration in `@BeforeClass` methods
   - Comprehensive logging with `.log().all()`, `.log().body()`, `.log().ifValidationFails()`

### API Endpoints Under Test

1. **Postman Jobs API** (`https://jobs.postmanatwork.com`):
   - `/jobs` - Get list of jobs with query parameters
   - `/jobs/{jobId}` - Get specific job by ID

2. **RESTful API Dev** (`https://api.restful-api.dev`):
   - `/objects/7` - Get specific object by ID
   - `/collections/{collectionName}/objects/{id}` - Get object from collection
   - `/collections/{collectionName}/objects` - Create new objects

### End-to-End Test Implementation Details

The `endtoendTest.java` file implements a complete end-to-end workflow:

1. **`createPostRequest()` method**:
   - Reads request data from `src/test/resources/request_data.json`
   - Sends a POST request to `https://api.restful-api.dev/collections/{collectionName}/objects` 
   - Captures the ID of the created object for subsequent validation
   - Validates the response status code and name field

2. **`validateProductByID()` method**:
   - Makes a GET request to `https://api.restful-api.dev/collections/{collectionName}/objects/{id}` using the captured ID
   - Validates that the response status code is 200 (OK)
   - Validates that the returned ID matches the one captured from the POST request
   - Validates that the name matches what was sent in the POST request

This implementation demonstrates a complete CRUD cycle: Create (POST) and Read (GET) operations with proper validation between the steps.

## Development Guidelines

### Adding New Tests

1. Follow the existing pattern of using `@BeforeClass` to set `baseURI`
2. Use descriptive method names that explain what is being tested
3. Include appropriate comments explaining the test purpose
4. Use proper logging (`.log().all()` for debugging, `.log().ifValidationFails()` for production)
5. Validate response times where performance matters
6. Use Hamcrest matchers for clear, readable assertions

### Best Practices Observed

1. **Parameter Usage**:
   - Query parameters: `.queryParam("key", "value")`
   - Path parameters: `.pathParam("paramName", "value")` with `{paramName}` in URL

2. **Response Validation**:
   - Status codes: `.statusCode(200)`
   - Content types: `.contentType(ContentType.JSON)`
   - Body content: `.body("json.path", matcher)`
   - Response time: `.time(lessThan(3000L))`

3. **Logging Strategies**:
   - Full logging: `.log().all()`
   - Body only: `.log().body()`
   - Conditional logging: `.log().ifValidationFails()`

4. **JSON Handling**:
   - Direct validation: `.body("data.field", equalTo("value"))`
   - Extraction with JsonPath: `JsonPath js = response.jsonPath()`

This project demonstrates comprehensive REST API testing techniques using REST Assured and serves as a reference for API automation best practices.