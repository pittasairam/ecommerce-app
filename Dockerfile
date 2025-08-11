# Stage 1: Build the application using Maven and JDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /ecommerce-app

# Copy Maven wrapper and pom.xml
COPY ecommerce-app/pom.xml .
COPY ecommerce-app/.mvn .mvn
COPY ecommerce-app/mvnw .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY ecommerce-app/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application using a lightweight JDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /ecommerce-app

# Copy the built jar file
COPY --from=build /ecommerce-app/target/*.jar ecommerce-app.jar

# Expose the application port
EXPOSE 9091

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "ecommerce-app.jar"]
