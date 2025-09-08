# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY ecomapp/pom.xml .
COPY ecomapp/.mvn .mvn
COPY ecomapp/mvnw .

# Fix permission issue for mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY ecomapp/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application using a lightweight JDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 9091

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
