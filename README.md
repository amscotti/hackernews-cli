# hackernews-cli

`hackernews-cli` is a small Kotlin command-line application that fetches and prints top stories from Hacker News.

This project is intentionally simple. It started as a Kotlin rewrite of the ideas from the original Go project at [`amscotti/hacker_news`](https://github.com/amscotti/hacker_news), with a focus on learning and experimenting rather than building a large feature set.

## Why This Project Exists

This repository is mainly an experiment in:

- using Kotlin for a small but real CLI application
- using Ktor Client and coroutines to model concurrent HTTP requests cleanly
- trying out GraalVM native images with a Kotlin application
- comparing the developer experience of JVM builds, shaded jars, Docker images, and native binaries

The app keeps the spirit of the original Go version, but explores how the same idea feels in Kotlin.

## Tech Stack

- Kotlin
- Ktor Client
- Kotlin coroutines
- kotlinx.serialization
- picocli
- GraalVM Native Image
- Gradle

## Build Outputs

This project can currently be built in a few different ways:

- standard Gradle build
- shaded jar via Shadow
- native executable via GraalVM
- Docker image

## Build Commands

Build the project:

```bash
./gradlew build
```

Build the shaded jar:

```bash
./gradlew shadowJar
```

Build the native executable:

```bash
./gradlew nativeCompile
```

Run the shaded jar:

```bash
java -jar build/libs/hackernews-cli-1.0-SNAPSHOT-all.jar -n 10 -u
```

Run the native executable:

```bash
./build/native/nativeCompile/hackernews-cli -n 10 -u
```

## Notes

This is not meant to be a full-featured Hacker News client. It is mostly a focused Kotlin learning project that uses a familiar idea to explore:

- idiomatic Kotlin code
- coroutine-based concurrency
- serialization and HTTP client integration
- native-image tradeoffs on the JVM

That combination is the main reason the project exists.
