# Use OpenJDK base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy built jar into the container
COPY target/upload-service-0.0.1-SNAPSHOT.jar upload-service.jar

# Expose the port
EXPOSE 8080

# Run the service
ENTRYPOINT ["java", "-jar", "upload-service.jar"]
