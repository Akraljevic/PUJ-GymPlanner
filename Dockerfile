# Use an official Maven image to build the application
FROM maven:3.9.7-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .

# Download maven dependencies (this will be cached)
RUN mvn dependency:go-offline -B

# Copy the rest of the project files
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Use an official Eclipse temurin runtime as a parent image
FROM eclipse-temurin:17

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/workoutplanner-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]