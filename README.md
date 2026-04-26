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
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Test Examples](#test-examples)
- [Contributing](#contributing)
- [License](#license)

## 📖 Overview

This project is a robust REST API testing framework designed to automate the testing of RESTful web services. It leverages the power of REST Assured, a Java-based DSL (Domain Specific Language) for easy testing of REST services, combined with TestNG for test execution and reporting.

The framework demonstrates various API testing techniques including:
- Query parameter testing
- Path parameter testing
- Response validation (status codes, headers, body content)
- Response time validation
- JSON schema validation
- End-to-end API workflows

## ✨ Features

- **Comprehensive API Testing**: Covers GET, POST, and other HTTP methods
- **Parameter Testing**: Both query and path parameter validation
- **Response Validation**: Status codes, headers, body content, and response time
- **Multiple API Endpoints**: Tests against real-world APIs like Postman Jobs and RESTful-API.dev
- **Detailed Logging**: Configurable logging for debugging and verification
- **Performance Testing**: Built-in response time validation
- **Modular Design**: Well-organized test classes for different functionalities
- **Extensible Framework**: Easy to add new tests and endpoints

## 🔧 Technologies Used

- **Java 21**: Programming language
- **REST Assured 5.4.0**: REST API testing library
- **TestNG 7.9.0**: Testing framework
- **Hamcrest 3.0**: Matcher objects for assertions
- **Jackson**: JSON serialization/deserialization library
- **Maven 3.0.0-M5**: Build automation tool
- **VS Code**: Development environment

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
│       │   ├── endtoendTest.java           # End-to-end API workflow
│       │   └── POJOExampleTest.java        # Examples of using POJO classes for JSON mapping
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

### Run all tests:
```bash
mvn test
```

### Run a specific test class:
```bash
mvn test -Dtest=QueryParamsTest
```

### Run a specific test method:
```bash
mvn test -Dtest=QueryParamsTest#getJobsByLocation
```

### Run tests with verbose output:
```bash
mvn test -Dsurefire.printSummary=true
```

### Run tests using TestNG suite XML:
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 🧪 Test Examples

### Query Parameter Testing
Tests API endpoints that accept query parameters like `/jobs?country=US`

### Path Parameter Testing
Tests API endpoints with path parameters like `/jobs/{jobId}`

### Response Validation
Validates status codes, headers, response time, and body content

### End-to-End Workflows
Complete API workflows including Create (POST), Read (GET), and Update (PUT) operations with proper validation between steps and response storage using POJO classes.

### POJO Integration
Examples of using Plain Old Java Objects (POJOs) for JSON serialization and deserialization, detailed in `POJOCLASS.md`.

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