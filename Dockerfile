FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /app

COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
RUN ./gradlew --no-daemon dependencies

COPY src ./src
RUN ./gradlew --no-daemon shadowJar

FROM eclipse-temurin:25-jre-alpine

WORKDIR /

COPY --from=builder /app/build/libs/hackernews-cli-1.0-SNAPSHOT-all.jar /hackernews-cli

ENTRYPOINT ["java", "-jar", "/hackernews-cli"]
