# Assembly Vote API Documentation
___

This project aims to develop a REST API for managing voting sessions in a cooperative. The API allows for the registration of agendas, opening of voting sessions, receiving votes, and tallying the results.

About the Service

The REST service that performs management between voting sessions. It uses MySql to store the data.

Here is what this little application demonstrates:

* Full integration with the latest  **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container : No need to install a container separately on the host just run using the java -jar command
* Writing a RESTfull service using annotation: support JSON request / response;
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* Spring Data Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations.
* Automatic CRUD functionality against the data source using Spring Repository pattern
* Demonstrates Junit test framework with associated libraries
* All APIs are "self-documented" by Swagger3 using annotations
* The code repository is the GitHub


# Project architecture

---
* The design pattern used in developing this API is called the "three-tier architecture pattern" or "MVC" (Model-View-Controller) pattern, often adapted in the Spring world as the "three-tier MVC" pattern.
* controller - Controllers are managing the REST interface, interacting with services to perform operations on data, and returning an appropriate response to the client.
* service - Contains business logic implementations, the middleware between Controller and Repository.
* repository - Repositories are interfaces that define methods for performing persistence operations on the database, such as saving, updating, deleting, and retrieving entities
* controler - classe for making calls to RESTfull services
* entity - Entities represent the domain objects in your application. In the context of Spring and JPA (Java Persistence API), entities are typically mapped to tables in the database.
* to - Is an object used to transfer data between application layers
* exception - Handles application Exception
* configuration - Application Settings
* mapper - mappings between data transfer objects and domain entities

# Bônus Tasks

---

This project completed 5 bonus task:

* Bonus Task 1 - Integration with External Systems
* Bonus Task 2 - Automatic Vote Counting
* Bonus Task 3 - Messaging and Queues
* Bonus Task 5 - Code Quality Analysis
* Bonus Task 6 - API Versioning


## Features - Available Endpoints
___

* **POST /agenda**: Create a new Agenda
* **POST /session**: Create a new session
* **POST /vote**: Create a new vote
* **GET /votecounting**: Return the counting of votes from a session

## Requirements
___

### For building and running the application, you need:
* Docker

## Building
___

1. Download the project and navigate to the project folder.
2. Build the Docker container:

``` docker-compose build ```

## Run

---
```docker-compose up ```


# Access for Testing
Although the application does not have a Member registration system, you can use the following pre-registered IDs for testing:

* ID 1: Test Member 1
* ID 2: Test Member 2
* ID 3: Test Member 3

Simply use these IDs in your requests to simulate interaction with the API.

---


## [API DOCS](http://localhost:8080/swagger-ui/index.html#/assembly-controller/findVoteCounting)

## [Console RabbitMq](link rabbitmq:http://localhost:15672)

