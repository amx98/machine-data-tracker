# Machine Data Tracker


## Introduction

This microservice is built with the framework Spring Boot and designed to consume messages coming from a RabbitMQ service. 
The consumed machine message from RabbitMQ will be persisted in a PostgreSQL database and provided via a REST API.



## Getting Started

To get started with the machine message microservice, follow the instructions in this README. 

### Prerequisites
Before you can run the microservice in a Docker Compose environment, 
you must have the following software and dependencies installed on your local machine:
* Docker version 20.10.21

    ```bash
    # show docker version
    $ docker --version
    ```
  
* Docker Compose version v2.3.3
    ```bash
    # show docker compose version
    $ docker compose version
    ```

## Running the application with Docker Compose
1. Go the root director of the repository
2. Run the following command to run the docker compose services.
   It will start a container of PostgreSQL database, RabbitMQ service and the Spring Boot application.

    ```bash
    # run spring-boot application, postgresql and rabbitmq
    $ docker compose up -d
    ```
3. You can then access the 
   1. Postgres database on localhost:5432, 
   2. RabbitMQ on localhost:5672, 
   3. RabbitMQ management UI on localhost:15672, 
   4. Spring Boot application on localhost:8080.
4. You can stop the services by running the following command.
    ```bash
    # delete the containers for PostgreSQL, RabbitMQ and the spring-boot application
    $ docker compose down
    ```
    

## Configuration of RabbitMQ
The application is configured to consume messages from a RabbitMQ service.
In order to receive messages it is required to configure message queues in RabbitMQ.
In this case we will define a message queue with the name "session-queue" for receiving sessions of machines
and another message queue with the name "session-events-queue" to receive events of the machines.

1. Go to the RabbitMQ management UI on localhost:15672
2. Login with the default credentials (username: guest, password: guest)
3. Go to the tab 'Queues and Streams' 
4. Create a new queue with the name "session-queue"
   1. Enter the name "session-queue"
   2. Click on "Add queue"
5. Go back to the tab 'Queues and Streams'
6. Create a new queue with the name "session-events-queue"
    1. Enter the name "session-events-queue"
    2. Click on "Add queue"
7. When you go back to the tab 'Queues and Streams' you should see the two queues in the list of queues.
![img_2.png](img/queues_and_streams.png)

## Publishing messages via RabbitMQ
In order to send messages to the application, you can publish messages in RabbitMQ management UI with the created queues.

### Publishing a session message
1. Go to the tab 'Queues and Streams'
2. Click on the queue "session-queue"
3. Enter the message in the field "Payload". 
You can find an example message below.

```json
    
{
 "sessionId": "mySessionId1",
 "machineId": "myMachineId1",
 "startAt": "2023-11-31T11:11:00"
}
```

### Publishing a session event message
1. Go to the tab 'Queues and Streams'
2. Click on the queue "session-queue"
3. Enter the message in the field "Payload".
   You can find an example message below.

```json
{
  "sessionId":"mySessionId1",
  "events":[
    {
      "eventAt":"1684327792",
      "eventType":"drivenDistance",
      "numericEventValue":500.00
    }
  ]
}
```


## Swagger Docs - Querying API Endpoints

Machine data can be queried via the REST API of the microservice.
The swagger UI can be access via following link:

http://localhost:8080/swagger-ui/index.html#/

The following endpoints are available:
### GET /machines/{machineId}/sessions/{sessionId}/events
This endpoint is used to retrieve a list of events associated with a  machine session.
It requires the machineId and sessionId parameters in the path and returns a list of event resources. 
It is useful for retrieving event data for a specific machine session.

### GET  /machines/{machineId}/sessions/recentSession (GET)
This endpoint is used to get information about the most recent session of a machine. 
It requires the machineId parameter in the path and returns a string, presumably identifying the recent session. 
It is used to obtain details about the latest session for a specific machine.

### GET  /machines/ (GET)
This endpoint is used to retrieve a list of all machines. 
It does not require any parameters and returns an array of strings. 
This endpoint is useful for getting a list of all the machines in the system.


## Development

### IDE

Intellj was used as IDE for developing the spring-boot microservice.

### Project Structure

The Spring Boot application follows a standard project structure.
Here's a brief overview of the main packages:

* controller: Contains REST controllers for external communication.
* messaging: Handles communication with RabbitMQ.
* services: The business layer that encapsulates the repository layer and provides functionality to other layers.
* repos: Implements the Data Access Object (DAO) layer for interacting with the PostgreSQL database.


### Test Execution

This app contains unit . To run them follow the step below:

#### Unit Tests

```bash
# run unit tests
$ ./gradlew test
```

# Improvements
1. Hide and encrypt secrets
   * All secrets need to be removed from the application property.
   * Env file needs to be encrypted (with e.g.  mozilla Sops)
   * Only encrypted version should be committed to the git repository ( Add .env to .gitignore)
2. Integrate H2 for testing repo functionalities
   * Configure and run H2 in-memory database for testing
3. Add parameter validation to endpoints
   * Add validation for path and request parameters
4. Implement exception handling
   * Currently, only the straight forward case are implemented without any exception handling. The exception handling should make the code more resilient and robust, by avoiding unexpected situations.
5. Introduce slf4j logging
   * Introduce logging to the application to make it easier to debug and monitor the application.
   * Remove all printing to console.
6. Add CI/CD scripts to testing and building artifacts with github actions
7. Add postgresql service with volume in docker-compose
   * Add volume for database to docker-compose to persist data in case of container restarts.
8. Add flyway migration scripts
   * Add flyway migration scripts to create database schema and tables.
   In addition, flyway can be used for schema migrations.
