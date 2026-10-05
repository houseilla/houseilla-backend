# Stage 1: Build stage (unaffected, compiles the app)
FROM maven:3.9.9-eclipse-temurin-24 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Production Runtime Stage
FROM eclipse-temurin:24-jre-alpine

# Security Best Practice: Run as a non-root user to protect the host system
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copy the compiled JAR file from the builder stage
COPY --from=builder --chown=spring:spring /build/target/houseilla-backend-0.0.1-SNAPSHOT.jar app.jar

# Optimize JVM settings for production containers (memory tuning)
ENV JAVA_OPTS="-XX:+UseG1GC -XX:+UseStringDeduplication"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
