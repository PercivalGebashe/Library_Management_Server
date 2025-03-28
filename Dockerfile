# Use OpenJDK as base image
FROM openjdk:17-jdk-slim

# Metadata
LABEL authors="percivalgebashe"

# Create a user for security
RUN useradd -m appuser

# Set working directory
WORKDIR /app

# Copy the JAR from lib directory (packaged by Maven)
COPY lib/*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appuser /app

# Switch to the non-root user
USER appuser

# Expose application port (default 5000)
EXPOSE 5000

# Allow overriding the port via an environment variable
ENTRYPOINT ["java", "-jar", "app.jar"]