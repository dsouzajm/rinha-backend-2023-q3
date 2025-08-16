# Stage 1: Build the application with Maven
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
#COPY pom.xml .
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final, smaller image
FROM openjdk:21-jdk-bullseye
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]