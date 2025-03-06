FROM openjdk:21
WORKDIR /app
ARG JAR_FILE=demo-0.0.1-SNAPSHOT.jar
COPY target/${JAR_FILE} /app/${JAR_FILE}
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]

EXPOSE 8030