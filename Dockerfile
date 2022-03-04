FROM gcr.io/distroless/java:latest

ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Xmx512m", "-jar","/app.jar"]
