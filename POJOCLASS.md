# POJO Classes in REST API Automation

## What are POJOs?

POJO stands for "Plain Old Java Object". In the context of REST API testing, POJOs are simple Java classes that represent the structure of JSON data exchanged with APIs. They provide a convenient way to:

1. **Deserialize** JSON responses from API calls into Java objects
2. **Serialize** Java objects into JSON for API requests
3. **Work with strongly-typed data** instead of raw JSON strings
4. **Enable IDE support** like autocomplete and compile-time checking

## POJO Classes in This Project

### 1. Product.java
Represents a single product entity with the following fields:
- `id` (String): Unique identifier for the product
- `name` (String): Product name
- `data` (ProductData): Nested object containing product specifications

### 2. ProductData.java
Represents the nested data structure within a product:
- `year` (int): Manufacturing year
- `price` (double): Product price
- `CPU_model` (String): CPU model information
- `Hard_disk_size` (String): Storage capacity
- `color` (String): Optional color attribute

### 3. ProductList.java
Container class for managing collections of products:
- `products` (List<Product>): List of Product objects

## How POJOs Work with JSON

### JSON to Java Object (Deserialization)

Given this JSON structure:
```json
{
  "id": "ff8081819d150699019d214dee9315c2",
  "name": "Apple MacBook Pro 16 Test",
  "data": {
    "year": 2019,
    "price": 1849.99,
    "CPU model": "Intel Core i9",
    "Hard disk size": "1 TB"
  }
}
```

The POJO classes automatically map JSON fields to Java object properties using Jackson annotations:

```java
// Jackson maps "CPU model" JSON field to CPU_model Java field
@JsonProperty("CPU model")
private String CPU_model;
```

## Implementing testJSONToPOJO() Method

Here's how to properly implement the `testJSONToPOJO()` method:

### 1. Basic Implementation

```java
@Test
public void testJSONToPOJO() throws IOException {
    // Create ObjectMapper instance
    ObjectMapper mapper = new ObjectMapper();
    
    // Read JSON file and deserialize to Product array
    Product[] products = mapper.readValue(
        new File("src/test/resources/all_response.json"), 
        Product[].class
    );
    
    // Verify deserialization worked
    assertNotNull(products);
    assertTrue(products.length > 0);
    
    // Access the first product
    Product firstProduct = products[0];
    assertNotNull(firstProduct.getId());
    assertNotNull(firstProduct.getName());
    assertNotNull(firstProduct.getData());
    
    System.out.println("Deserialized " + products.length + " products successfully");
    System.out.println("First product: " + firstProduct);
}
```

### 2. Advanced Implementation with Error Handling

```java
@Test
public void testJSONToPOJOAdvanced() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    
    try {
        // Read JSON file
        Product[] products = mapper.readValue(
            new File("src/test/resources/all_response.json"), 
            Product[].class
        );
        
        // Process each product
        for (Product product : products) {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            
            ProductData data = product.getData();
            if (data != null) {
                System.out.println("Year: " + data.getYear());
                System.out.println("Price: $" + data.getPrice());
                System.out.println("CPU: " + data.getCPU_model());
                System.out.println("Storage: " + data.getHard_disk_size());
                
                // Handle optional color field
                if (data.getColor() != null) {
                    System.out.println("Color: " + data.getColor());
                }
            }
            System.out.println("---");
        }
        
        // Assertions to validate data
        assertTrue(products.length >= 1, "Should have at least one product");
        
    } catch (IOException e) {
        fail("Failed to deserialize JSON: " + e.getMessage());
    }
}
```

### 3. Using with REST Assured Responses

You can also use POJOs directly with REST Assured:

```java
@Test
public void testAPIResponseWithPOJO() {
    // Make API call and deserialize directly to POJO
    Product[] products = given()
        .header("x-api-key", "0133e888-359b-497d-a1e2-a4f8255956e0")
        .pathParam("collectionName", "testproducts")
        .when()
        .get("https://api.restful-api.dev/collections/{collectionName}/objects")
        .then()
        .statusCode(200)
        .extract()
        .as(Product[].class); // Direct POJO deserialization
    
    // Work with strongly-typed objects
    for (Product product : products) {
        System.out.println("Product: " + product.getName());
    }
}
```

## Benefits of Using POJOs

### 1. Type Safety
```java
// Without POJOs - error-prone string manipulation
String jsonString = response.asString();
// Manual parsing required...

// With POJOs - compile-time checking
Product[] products = response.as(Product[].class);
Product firstProduct = products[0];
String productName = firstProduct.getName(); // Type-safe
```

### 2. IDE Support
- Autocomplete for field names
- Compile-time error detection
- Refactoring support
- Better debugging experience

### 3. Code Readability
```java
// Less readable
String name = jsonObject.get("data").getAsJsonObject().get("name").getAsString();

// More readable
String name = product.getData().getName();
```

## Best Practices

### 1. Use Jackson Annotations
```java
@JsonProperty("CPU model")  // Maps JSON field name to Java field
private String CPU_model;

@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore unknown fields
public class ProductData {
    // ...
}
```

### 2. Handle Optional Fields
```java
// Check for null before using optional fields
if (productData.getColor() != null) {
    System.out.println("Color: " + productData.getColor());
}
```

### 3. Validate Data
```java
@Test
public void testProductValidation() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    Product[] products = mapper.readValue(
        new File("src/test/resources/all_response.json"), 
        Product[].class
    );
    
    for (Product product : products) {
        assertNotNull(product.getId(), "Product ID should not be null");
        assertNotNull(product.getName(), "Product name should not be null");
        assertNotNull(product.getData(), "Product data should not be null");
        assertTrue(product.getData().getYear() > 2000, "Year should be reasonable");
    }
}
```

## Common Issues and Solutions

### 1. Field Name Mismatches
**Problem**: JSON uses "CPU model" but Java convention is camelCase
**Solution**: Use `@JsonProperty("CPU model")`

### 2. Unknown Fields in JSON
**Problem**: API adds new fields that aren't in POJO
**Solution**: Add `@JsonIgnoreProperties(ignoreUnknown = true)`

### 3. Null Pointer Exceptions
**Problem**: Accessing fields that might be null
**Solution**: Always check for null before accessing nested objects

## Conclusion

POJO classes provide a robust, type-safe way to work with JSON data in REST API testing. They integrate seamlessly with REST Assured and Jackson, making your tests more reliable and maintainable. By using POJOs, you can focus on testing business logic rather than parsing JSON strings.