FROM openjdk:17

WORKDIR /app

COPY target/rinha-java-0.0.1-SNAPSHOT.jar /app/simple-mvc-java.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "simple-mvc-java.jar"]