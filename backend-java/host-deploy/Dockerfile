# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file to the container
COPY fobov-0.0.1-SNAPSHOT.jar app.jar

# Copy the SQLite database file into the container
COPY database.db /app/database.db

# Set the environment variable to point to the database file location inside the container
ENV DATABASE_FILE_PATH=/app/database.db

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar", "--debug"]