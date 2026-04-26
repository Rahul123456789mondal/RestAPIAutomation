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
│       │   ├── endtoendTest.java           # End-to-end API workflow
│       │   └── POJOExampleTest.java        # Examples of using POJO classes for JSON mapping
│       └── resources/
│           ├── request_data.json           # Sample JSON payload data for POST requests
│           ├── put_request.json            # Sample JSON payload data for PUT requests
│           └── all_response.json           # Sample response data
├── pom.xml                                 # Maven configuration
├── POJOCLASS.md                           # Detailed POJO implementation guide
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

# Run tests using TestNG suite XML
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Clean and rebuild
mvn clean install
```

### TestNG Configuration

The project includes TestNG XML configuration files that organize tests into logical suites:

1. **Query Parameter Tests** - Tests API endpoints with query parameters
2. **Path Parameter Tests** - Tests API endpoints with path parameters
3. **Response Validation Tests** - Comprehensive response validation techniques
4. **Year Response Validation Tests** - Specific field validation and response time checks
5. **Product Creation Workflow** - End-to-end product creation and validation
6. **Product Management Workflow** - Product retrieval and update operations

To run specific test suites, use the TestNG XML configuration with Maven.

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
   - `POJOExampleTest`: Examples of using POJO classes for JSON mapping
   - `GetRequestExample`: Basic standalone example for manual execution

2. **POJO Classes**:
   - `Product`: Represents a product entity with id, name, and data fields
   - `ProductData`: Represents the nested data structure within a product
   - `ProductList`: Container class for a list of products

3. **Frameworks Used**:
   - REST Assured: Primary API testing framework
   - TestNG: Test execution framework
   - Hamcrest: Assertion library for expressive validations
   - Jackson: JSON serialization/deserialization library

4. **Documentation**:
   - `POJOCLASS.md`: Comprehensive guide to POJO implementation and usage

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

The `endtoendTest.java` file implements a complete end-to-end workflow using POJO classes for JSON handling:

1. **`createPostRequest()` method**:
   - Reads request data from `src/test/resources/request_data.json`
   - Sends a POST request to `https://api.restful-api.dev/collections/{collectionName}/objects`
   - Captures the ID of the created object for subsequent validation
   - Validates the response status code and name field
   - Uses POJO classes to deserialize the response for type-safe access to product data

2. **`validateProductByID()` method**:
   - Makes a GET request to `https://api.restful-api.dev/collections/{collectionName}/objects/{id}` using the captured ID
   - Validates that the response status code is 200 (OK)
   - Validates that the returned ID matches the one captured from the POST request
   - Validates that the name matches what was sent in the POST request
   - Uses POJO classes to deserialize the response for easy access to product attributes

3. **`fetchAllProducts()` method**:
   - Makes a GET request to `https://api.restful-api.dev/collections/{collectionName}/objects` to fetch all products
   - Stores the response as a List of Product POJOs (`fetchedProducts`) for use by other methods
   - Validates that the response status code is 200 (OK)
   - Uses POJO classes to deserialize the response for type-safe access to product data

4. **`updateProductByID()` method**:
   - Reads request data from `src/test/resources/put_request.json`
   - Sends a PUT request to `https://api.restful-api.dev/collections/{collectionName}/objects/{id}`
   - Uses the ID captured from the `createPostRequest()` method
   - Sends authentication via `x-api-key` header and `Content-Type: application/json` header
   - Attaches the JSON body from the put_request.json file
   - Validates that the response status code is 200 (OK)
   - Uses POJO classes to deserialize the response for easy access to updated product data
   - Displays the updated product information including name, year, price, CPU model, and hard disk size

This implementation demonstrates a complete CRUD cycle: Create (POST), Read (GET), Update (PUT) operations with proper validation between the steps and response storage using POJO classes for further processing.

## Development Guidelines

### Adding New Tests

1. Follow the existing pattern of using `@BeforeClass` to set `baseURI`
2. Use descriptive method names that explain what is being tested
3. Include appropriate comments explaining the test purpose
4. Use proper logging (`.log().all()` for debugging, `.log().ifValidationFails()` for production)
5. Validate response times where performance matters
6. Use Hamcrest matchers for clear, readable assertions

### Working with JSON Data

1. Use POJO classes for JSON serialization/deserialization when working with complex data structures
2. Refer to `POJOCLASS.md` for detailed implementation guidelines
3. Use Jackson annotations (`@JsonProperty`, `@JsonIgnoreProperties`) for proper field mapping
4. Handle optional fields by checking for null values before accessing them

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