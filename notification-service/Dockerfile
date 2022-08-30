FROM openjdk:13-jdk-alpine
COPY target/notification-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh"," bill-service:8082","--timeout=110","--","java", "-jar", "notification-service-1.0-SNAPSHOT.jar"]