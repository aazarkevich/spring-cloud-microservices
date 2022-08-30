FROM openjdk:13-jdk-alpine
EXPOSE 8081
COPY target/account-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh","gateway:8989","--timeout=70","--", "java", "-jar", "account-service-1.0-SNAPSHOT.jar"]