# Build stage
FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app

COPY ./wait-for-it.sh .
RUN chmod +x ./wait-for-it.sh
RUN ./wait-for-it.sh db:5432
RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM openjdk:21-jdk-bullseye
COPY --from=build /home/app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
