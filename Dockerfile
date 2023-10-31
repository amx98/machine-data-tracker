FROM gradle:8.4.0-jdk17 AS builder
WORKDIR /machine-data-tracker

COPY build.gradle.kts settings.gradle.kts ./

COPY src src

RUN gradle build --no-daemon

RUN ls -la /machine-data-tracker/

FROM openjdk:17-jdk-slim
WORKDIR /machine-data-tracker

COPY --from=builder /machine-data-tracker/build/libs/machine-data-tracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]