# Use OpenJDK as base image
FROM openjdk:17-jdk-slim

LABEL authors="percivalgebashe"

# Set working directory
WORKDIR /app

# Copy the JAR from lib directory (packaged by Maven)
COPY lib/*.jar app.jar

# Expose application port
EXPOSE 8090

# Run the JAR
CMD ["java", "-jar", "app.jar"]
