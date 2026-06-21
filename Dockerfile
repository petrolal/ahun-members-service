FROM eclipse-temurin:25-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-Xmx256m","-jar","/app.jar"]