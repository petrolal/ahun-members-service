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
ENTRYPOINT ["sh", "-c", "java \
  -Dspring.datasource.url=$SPRING_DATASOURCE_URL \
  -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME \
  -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD \
  -Dtelegram.bot-token=$TELEGRAM_BOT_TOKEN \
  -Dtelegram.chat-id=$TELEGRAM_CHAT_ID \
  -Dgoogle.credentials=$GOOGLE_CREDENTIALS \
  -Xmx256m -jar app.jar"]