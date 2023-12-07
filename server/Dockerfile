FROM openjdk:21-jdk-bullseye

WORKDIR /app

COPY . .

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Create jar
CMD ["./wait-for-it.sh", "db:5432", "--", "mvn", "install"]

EXPOSE 8080

RUN chmod +x ./wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh", "db:5432", "--", "java", "-jar", "target/app.jar"]
