# Smart Campus: Sensor & Room Management API

**School Of Computing Informatics Institute of Technology**
 
**5COSC022C.2:** Client-Server Architectures

**Coursework Type:** Individual Java project



## Student Details

**Student Name:** Thejeswar Dinesh Jeeva

**Student UOW ID:** w2153390

**Student IIT ID:** 20232892

---

## Introduction
The Smart Campus API is a RESTful service designed to manage rooms and sensors within a campus environment. It allows clients to create and manage rooms, assign sensors to those rooms, and record sensor readings. The API follows REST principles using standard HTTP methods and resource-based URIs. It is implemented using Java with Jersey (JAX-RS) and runs on a Grizzly HTTP server.

---

## Tech Stack

| Technology | Purpose |
|-----------|--------|
| Java 17 | Core programming language |
| Jersey (JAX-RS) | REST API framework |
| Grizzly HTTP Server | Embedded server |
| Maven | Build and dependency management |

---

## API Endpoints

| Method | Endpoint | Description |
|-------|---------|------------|
| GET | /api/v1/rooms | Get all rooms |
| GET | /api/v1/rooms/{id} | Get room by ID |
| POST | /api/v1/rooms | Create room |
| DELETE | /api/v1/rooms/{id} | Delete room |
| GET | /api/v1/rooms/{id}/sensors | Get sensors in a room |
| GET | /api/v1/sensors | Get all sensors |
| GET | /api/v1/sensors/{id} | Get sensor by ID |
| POST | /api/v1/sensors | Create sensor |
| DELETE | /api/v1/sensors/{id} | Delete sensor |
| PUT | /api/v1/sensors/{id}/reading | Add sensor reading |
| GET | /api/v1/sensors/{id}/readings | Get sensor readings |


---

## Project Structure

```
smart-campus-api/
├── pom.xml
└── src/main/java/com/smartcampus/
    ├── SmartCampusAPI.java                          
    ├── model/
    │   ├── Booking.java                      
    │   ├── Room.java      
    │   ├── Sensor.java                                  
    │   ├── SensorReading.java                        
    ├── storage/
    │   └── DataStore.java            
    ├── resource/
    │   ├── DiscoveryResource.java         
    │   ├── RoomResource.java              
    │   ├── SensorResource.java  
    │   ├── SensorReadingResource.java    
    │   └── TestResource.java     
    ├── exception/
    │   ├── BadRequestException.java     
    │   ├── ResourceNotFoundException.java  
    │   ├── ErrorResponse.java  
    │   ├── LinkedResourceNotFoundException.java  
    │   ├── SensorUnavailableException.java  
    ├── mapper/
    │   ├── BadRequestMapper.java             
    │   ├── GlobalExceptionMapper.java
    │   ├── LinkedResourceNotFoundException.java
    │   ├── ResourceNotFoundMapper.java
    │   ├── SensorUnavailableExceptionMapper.java
    ├── filter/
    │   ├── LoggingFilter.java
    └── config/
        └── ApplicationConfig.java                    
```


---

## Build and Run

To run this project, ensure that Git, Java 17, and Maven are installed on your system.

 First, clone the repository from GitHub using `git clone https://github.com/<your-username>/smart-campus-api.git`, 
 
 then navigate into the project directory using `cd smart-campus-api`. 
 
 Once inside the project, build it by running `mvn clean install`, which will compile the code and download all required dependencies. 
 
 After a successful build, start the application using `mvn exec:java -Dexec.mainClass="com.smartcampus.SmartCampusApi"`.
 
  When the server starts, it will be accessible at `http://localhost:8080/api/v1/`, and you can test the API endpoints using tools such as Postman or curl. Ensure that Java 17 is being used by running `java -version`, and make sure that port 8080 is not occupied by another application. To stop the server, terminate the process using `Ctrl + C`.

---

---

## Sample CURL Commands

Below are sample curl commands demonstrating successful interactions with different parts of the API.

### 1. Create a Room

```bash
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{
  "id": "ROOM-001",
  "name": "Lecture Hall",
  "capacity": 100
}'
```

### 2. Get All Rooms

```bash
curl -X GET http://localhost:8080/api/v1/rooms
```
---

### 3. Create a Sensor

```bash
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{
  "id": "TEMP-001",
  "type": "Temperature",
  "status": "ACTIVE",
  "currentValue": 0,
  "roomId": "ROOM-001"
}'
```

### 4. Add a Sensor Reading

```bash
curl -X PUT http://localhost:8080/api/v1/sensors/TEMP-001/reading \
-H "Content-Type: application/json" \
-d '{
  "value": 25.5
}'
```

### 5. Get Sensors in a Room

```bash
curl -X GET http://localhost:8080/api/v1/rooms/ROOM-001/sensors
```

### 6. Delete a Room

```bash
curl -X DELETE http://localhost:8080/api/v1/rooms/ROOM-001
```



# Smart Campus API – Conceptual Report (Questions & Answers)

---

## Part 1: Service Architecture & Setup

### 01 - Question:
In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures.

### Answer:
In JAX-RS, resource classes are request-scoped by default, meaning a new instance is created for each incoming request. This ensures that each request is handled independently and avoids shared state within the resource class. However, shared in-memory data structures such as maps in the DataStore are accessed concurrently by multiple threads. Without proper synchronization, this can lead to race conditions or inconsistent data. To prevent this, thread-safe collections such as ConcurrentHashMap should be used.

```java
public static Map<String, Room> rooms = new ConcurrentHashMap<>();
```

### 02 - Question:
Why is the provision of Hypermedia (HATEOAS) considered a hallmark of advanced RESTful design? How does this approach benefit client developers compared to static documentation?

### Answer:
Hypermedia allows the server to include links in API responses so clients can dynamically discover available actions. This reduces reliance on static documentation and hardcoded endpoints. Clients can navigate the API using provided links, making the system more flexible and easier to maintain as it evolves.

```java
@GET
@Path("/")
public Map<String, String> discovery() {
    Map<String, String> links = new HashMap<>();
    links.put("rooms", "/api/v1/rooms");
    links.put("sensors", "/api/v1/sensors");
    return links;
}
```
## Part 2: Room Management

### 01 - Question:
When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client-side processing.

### Answer:
Returning only IDs reduces the size of the response and saves network bandwidth, but it requires clients to make additional requests to retrieve full details. Returning full room objects increases the payload size but simplifies client-side processing by providing all required information in one response. In most cases, returning full objects improves usability and reduces the number of API calls.

```java
@GET
public Collection<Room> getAllRooms() {
    return DataStore.rooms.values();
}
```

### 02 - Question:
Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the same DELETE request multiple times.

### Answer:
The DELETE operation is idempotent because performing the same request multiple times results in the same final system state. The first request deletes the resource, while subsequent requests do not change the system further. Even if an error is returned for repeated requests, the resource remains deleted, which satisfies the idempotency requirement.

```java
@DELETE
@Path("/{id}")
public void deleteRoom(@PathParam("id") String id) {
    if (!DataStore.rooms.containsKey(id)) {
        throw new ResourceNotFoundException("Room not found");
    }
    DataStore.rooms.remove(id);
}
```

## Part 3: Sensor Operations & Linking

### 01 - Question:
Explain the technical consequences if a client attempts to send data in a different format when @Consumes(MediaType.APPLICATION_JSON) is used.

### Answer:
When a method specifies that it consumes JSON, the server expects all incoming requests to be in JSON format. If a client sends data in another format such as text or XML, JAX-RS automatically rejects the request and returns a 415 Unsupported Media Type error. This ensures that only valid and expected data formats are processed by the API.

```java
@POST
@Consumes(MediaType.APPLICATION_JSON)
public Sensor createSensor(Sensor sensor) {
    return sensor;
}
```

### 01 - Question:
Why is using @QueryParam for filtering considered superior to including the filter in the URL path?

### Answer:
Query parameters are designed for filtering and allow flexible queries with multiple conditions in a single request. Path parameters are better suited for identifying specific resources rather than filtering collections. Using query parameters results in a cleaner and more scalable API design.

```java
@GET
public List<Sensor> getSensorsByType(@QueryParam("type") String type) {
    return DataStore.sensors.values()
        .stream()
        .filter(s -> type == null || s.getType().equalsIgnoreCase(type))
        .toList();
}
```

## Part 4: Deep Nesting with Sub-Resources

### 01 - Question:
Discuss the architectural benefits of the Sub-Resource Locator pattern.

### Answer:
The sub-resource locator pattern allows nested resources to be handled by separate classes instead of placing all logic in a single controller. This improves code organization and makes the API easier to maintain and extend. As the system grows, this approach prevents the main resource class from becoming overly complex.

```java
@Path("/rooms/{id}/sensors")
public SensorResource getSensorResource() {
    return new SensorResource();
}
```

## Part 5: Advanced Error Handling, Exception Mapping & Logging

### 01 - Question:
Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

### Answer:
HTTP 422 indicates that the request is syntactically correct but contains invalid data. This is more accurate than 404, which implies that a resource does not exist at the endpoint level. Using 422 provides clearer feedback when the issue lies within the request content rather than the resource itself.

```java
throw new WebApplicationException("Invalid roomId", 422);
```

### 02 - Question:
From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers.

### Answer:
Exposing stack traces can reveal sensitive information such as internal class names, file paths, and system structure. Attackers can use this information to identify vulnerabilities and plan targeted attacks. To prevent this, APIs should return generic error messages instead of exposing internal details.

```java
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    public Response toResponse(Exception ex) {
        return Response.status(500)
                .entity("Internal Server Error")
                .build();
    }
}
```

### 03 - Question:
Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting logging statements in every resource method?

### Answer:
Using JAX-RS filters centralizes logging logic and avoids repeating code across multiple resource methods. This ensures consistent logging for all requests and keeps resource classes clean and focused on business logic. It also makes the application easier to maintain and update.


```java
@Provider
public class LoggingFilter implements ContainerRequestFilter {
    public void filter(ContainerRequestContext requestContext) {
        System.out.println("Request: " + requestContext.getMethod() + " " + requestContext.getUriInfo().getPath());
    }
}
```

## Error Response Format

All errors in the API follow a consistent JSON structure to provide clear and meaningful feedback to clients. This ensures that clients can easily understand what went wrong and handle errors appropriately.

## Format

```json
{
  "status": 400,
  "message": "Error description",
  "timestamp": 1713860000000
}
```
## Field Descriptions

| Field | Description |
|-----------|----------------------------------------------------------|
| status | The HTTP status code of the response (e.g., 400, 404, 500) |
| messsage | A short, human-readable description of the error |
| timestamp | The time the error occurred (in milliseconds since epoch) |

## Example Responses

### 400 Bad Request

```json
{
  "status": 400,
  "message": "Room ID is required",
  "timestamp": 1713860000000
}
```

### 404 Not Found

```json
{
  "status": 404,
  "message": "Room not found",
  "timestamp": 1713860000000
}
```

### 422 Unprocessable Entity

```json
{
  "status": 422,
  "message": "Invalid roomId",
  "timestamp": 1713860000000
}
```

### 500 Internal Server Error


```json
{
  "status": 500,
  "message": "Internal Server Error",
  "timestamp": 1713860000000
}
```

