# Stage 1: Build
FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /app
# Copy only necessary files
COPY build.gradle.kts settings.gradle.kts ./
COPY gradlew ./
COPY gradle ./gradle
COPY src ./src

# Build the executable jar. GitHub Packages credentials are supplied as
# BuildKit secrets by CI (docker/build-push-action `secrets:` input) so they
# never end up in an image layer. Locally you can pass them with:
#   DOCKER_BUILDKIT=1 docker build \
#     --secret id=github_actor,env=GITHUB_ACTOR \
#     --secret id=github_token,env=GITHUB_TOKEN .
RUN --mount=type=secret,id=github_actor \
    --mount=type=secret,id=github_token \
    GITHUB_ACTOR="$(cat /run/secrets/github_actor 2>/dev/null || true)" \
    GITHUB_TOKEN="$(cat /run/secrets/github_token 2>/dev/null || true)" \
    ./gradlew bootJar --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
# Copy only the jar file
COPY --from=builder /app/build/libs/*.jar app.jar
# Define user with right groups
RUN addgroup -S ahun && adduser -S ahun -G ahun
USER ahun
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:TieredStopAtLevel=1", "-Xss256k", "-Xms32m", "-Xmx192m", "-jar", "app.jar"]
