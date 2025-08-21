# syntax=docker/dockerfile:1

### Build-Stage
FROM maven:3.9.3-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Caching der Dependencies
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Code rein und bauen
COPY src ./src
RUN mvn -q -DskipTests package

### Run-Stage (schlankes JRE)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# JAR übernehmen (nur das fertige Jar)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
