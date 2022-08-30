FROM openjdk:13-jdk-alpine
EXPOSE 8001
COPY target/config-service-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "config-service-1.0-SNAPSHOT.jar"]