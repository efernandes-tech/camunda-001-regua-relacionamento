# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .

# Download dependencies; if you have a settings.xml, you might want to copy it as well
RUN mvn dependency:go-offline

# Copy the entire project and package the application
COPY . .

# Build the application, skipping tests to speed up the process
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-alpine AS run

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
