FROM gradle:7.6 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM openjdk:17-jdk-alpine
COPY --from=build /home/gradle/src/build/libs/pcbuildermkapi-0.0.1-SNAPSHOT.jar /app/pcbuildermkapi.jar
