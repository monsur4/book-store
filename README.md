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

    `/mvnw clean test`

Build the application

    `./mvnw clean build`

Start the application

    `java -jar target/book-store-0.0.1-SNAPSHOT.jar`
The application should start up on port 8080

## Building the Docker image
CD into the root directory of this module

Run the command below to create the jar file locally on a host machine

    ./mvnw clean install

This creates a jar file located in `target/book-store-0.0.1-SNAPSHOT.jar`

Then build the Docker image

    docker build -t book-store:v1 .