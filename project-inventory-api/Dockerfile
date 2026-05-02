# Build stage
FROM eclipse-temurin:25-jdk-jammy AS build
WORKDIR /workspace

# Copy Maven wrapper and project definition first for dependency caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source tree
COPY src ./src

# Build the Spring Boot application
RUN chmod +x mvnw && ./mvnw -B package -DskipTests

# Runtime stage
FROM eclipse-temurin:25-jre-jammy
WORKDIR /app

# Copy only the application jar from the build stage
COPY --from=build /workspace/target/project-inventory-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
