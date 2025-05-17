# Use a Maven image to build the application
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app

# Copy the pom and source files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK runtime image for deployment
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/trackingapi-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
