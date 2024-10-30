# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper script and its properties files
COPY mvnw ./
COPY .mvn .mvn

# Ensure mvnw is executable
RUN chmod +x mvnw

# Copy the application's JAR file into the container
# Ensure you have a 'target' directory where your Spring Boot project builds the JAR
COPY target/gestao_usuarios.jar app.jar

# Expose the application's port (Spring Boot default is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
