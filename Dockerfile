FROM maven:3.9.5-amazoncorretto-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package

FROM amazoncorretto:17-alpine AS deploy

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]