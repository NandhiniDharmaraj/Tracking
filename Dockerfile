FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/trackingapi-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]