FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

# Expose the port your Spring Boot app uses
EXPOSE 8093

ENTRYPOINT ["java", "-jar", "app.jar"]

