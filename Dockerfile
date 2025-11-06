FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/cow-report-backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
