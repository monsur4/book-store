# Book Store

This is a repository for a basic book store application

## Getting Started

You'll need to meet the requirements below to have the service running on your machine.

## Prerequisites
Requirements for the software and other tools to build, test and push
- [Docker](https://www.docker.com)
- [Java 17 or above](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- maven

## Setup
Do the following to start the application locally

Clone the repo
    
    `git clone https://github.com/monsur4/book-store.git`

Run the tests

    `./mvnw clean test`

Build the application

    `./mvnw clean build`

Start the application

    `java -jar target/book-store-0.0.1-SNAPSHOT.jar`
The application should start up on port 8080

## Building the Docker image
CD into the root directory of this module

Then build the Docker image

    docker build -t book-store:v1 .

### To Start the image built in the previous section run:

      docker run -p 8080:8080 book-store:v1
the service will be available on port 8080

### Alternatively you can start a container from the docker hub image:

      docker run -p 8080:8080 monsuru/book-store:1.0.1
the service will be available on port 8080

### NB: 
1. This service uses basic authentication

        username = test
        password = test
2. An h2 in-memory database on the development profile

        dburl = url=jdbc:h2:mem:test
         user = test123
         password = test123
3. Find the api documentation on swagger ui, available at 

        http://localhost:8080/api/v1/swagger-ui/index.html