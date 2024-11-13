# Dockerfile for a Spring Boot application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080  # Change this to the actual port for each service if necessary
ENTRYPOINT ["java", "-jar", "app.jar"]

