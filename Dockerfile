# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*-jar-with-dependencies.jar app.jar
COPY --from=build /app/database/vicfreewifi20ap20map20data2020170724.db database/
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]