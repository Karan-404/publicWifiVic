# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:16-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build the project
RUN mvn clean package

# Use only the JAR in the final image
FROM openjdk:16-jdk-slim
WORKDIR /app
COPY --from=build /app/target/week05-1.0-SNAPSHOT.jar app.jar
EXPOSE 7002
