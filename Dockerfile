# Use the official OpenJDK image as base
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/contact-system-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

COPY .env /app/.env

# Define entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
