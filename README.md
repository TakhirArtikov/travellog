## Travel Log Backend Application
This is a backend application for managing travel logs for personal cars used for company needs. The application provides functionalities for adding, editing, and deleting log entries, as well as generating a report based on existing logs.

Technologies Used

Java 17

Spring Boot

PostgreSQL

Gradle

Liquibase

Requirements:

Java 17

PostgreSQL


Installation and Running Guide
Clone or download this repository.
Navigate to the project directory.
Open the src/main/resources/application.properties file and configure the following properties:
spring.datasource.url: the URL for the PostgreSQL database.
spring.datasource.username: the username for the PostgreSQL database.
spring.datasource.password: the password for the PostgreSQL database.
Open the src/main/resources/liquibase.properties file and configure the following properties:
url: the URL for the PostgreSQL database.
username: the username for the PostgreSQL database.
password: the password for the PostgreSQL database.
Run the following command to start the application:

```./gradlew bootRun```

The application will start on port 8080. You can access the Swagger UI for the API documentation by navigating to the following URL in your web browser:

```http://localhost:8080/swagger-ui.html```

API Endpoints
The API provides the following endpoints:

POST /travel_log: adds a new log entry

PUT /travel_log/{id}: updates an existing log entry by ID

DELETE /travel_log/{id}: deletes an existing log entry by ID

GET /travel_log: retrieves a list of log entries filtered by date range, vehicle registration number, and/or vehicle owner's name

POST /travel_log/report: generates a report of log entries grouped by date and sorted by odometer initial value, filtered by date range, vehicle registration number, and/or vehicle owner's name


Data Model
The application uses the following data model:

VehicleLog: represents a travel log entry
date: the date of the journey
vehicleNumber: the registration number of the vehicle
vehicleOwner: the name of the vehicle owner
odometerStart: the odometer value at the beginning of the journey
odometerEnd: the odometer value at the end of the journey
route: the route of the journey
description: a short description of the journey

