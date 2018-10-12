FROM maven:3.5.4-jdk-8-alpine as builder
RUN mkdir /app
COPY ./pom.xml /app
RUN mkdir /app/src/
COPY ./src /app/src/
RUN ls /app
RUN cd /app && mvn clean package


FROM openjdk:8-jdk-alpine
COPY --from=builder /app/target/FileToImage-0.1-jar-with-dependencies.jar /
