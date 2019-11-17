FROM openjdk:11-jre-slim

MAINTAINER Marcus Vieira <marcusvinicius.viera88@gmail.com>

RUN mkdir /app
COPY ./target/flight-in-portugal-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "flight-in-portugal-0.0.1-SNAPSHOT.jar"]