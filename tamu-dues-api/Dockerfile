# ===== Stage 1: Build the JAR =====
FROM maven:3.9.11-eclipse-temurin-25 AS builder

WORKDIR /app

# Copy Maven files first (for dependency caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ===== Stage 2: Run the JAR =====
FROM eclipse-temurin:25-jdk

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port (default 8080)
EXPOSE 8080

# Default environment variables (override in Compose if needed)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/mydb \
    SPRING_DATASOURCE_USERNAME=root \
    SPRING_DATASOURCE_PASSWORD=root

ENTRYPOINT ["java", "-jar", "app.jar"]
