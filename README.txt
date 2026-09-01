# Awélé Game

Client-server implementation of the traditional Awélé board game, developed in Java.

The project uses a Spring Boot REST API for the server and a JavaFX application for the client. Communication between both applications is defined using OpenAPI.

## Technologies

- Java 21
- Spring Boot
- JavaFX
- Maven
- REST API
- OpenAPI / Swagger

## Architecture

The project is divided into two main modules:

- `awele-server` — Spring Boot REST API containing the game logic
- `awele-client` — JavaFX client communicating with the server

## Features

- Player connection
- Game board display
- Player moves
- Automatic opponent moves
- Game state management
- Player disconnection

## Running the project

### Server

Open the `awele-server` module and run:

`AweleServerApplication`

The API is available at:

`http://localhost:8080/v1`

### Client

Open the `awele-client` module and run:

`client.Main`

JavaFX 21 is required to run the graphical interface.

## OpenAPI

The OpenAPI specification can be found at:

`awele-client/src/main/resources/awele-api-3.yaml`

The client API classes are generated using OpenAPI Generator.

## Author

Alexandre Pugliese