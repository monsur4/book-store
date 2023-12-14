FROM amazoncorretto:17.0.8-al2023
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]