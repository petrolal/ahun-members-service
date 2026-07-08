# Stage 1: Build
FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /app
# Copy only necessary files
COPY build.gradle.kts settings.gradle.kts ./
COPY gradlew ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
# Copy only the jar file
COPY --from=builder /app/build/libs/*.jar app.jar
# Define user with right groups
RUN addgroup -S ahun && adduser -S ahun -G ahun
USER ahun
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:TieredStopAtLevel=1", "-Xss256k", "-Xms32m", "-Xmx192m", "-jar", "app.jar"]