FROM openjdk:8-jdk-alpine
MAINTAINER pedram.karevan@gmail.com
COPY ./target target
ENTRYPOINT ["java","-jar","url-shortener-0.0.1-SNAPSHOT.jar"]