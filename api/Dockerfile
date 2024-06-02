# Stage 1: Build the project

# This line specifies the base image for the build stage, which includes Maven and OpenJDK 17.
FROM maven:3.8.4-openjdk-17-slim AS build

# Sets the working directory inside the container to /app.
WORKDIR /app

# Copies the pom.xml file from your local machine to the container's working directory.
# This file is necessary for Maven to resolve dependencies.
COPY pom.xml .

# Copies the src directory, which contains your Java source code,
# from your local machine to the container.
COPY src ./src

# Executes the Maven command to build the project. This command will:
#  - Clean the previous builds (clean).
#  - Compile the source code.
#  - Package the compiled code into a JAR file (package).
#  - Skip running tests to speed up the build process (-DskipTests).
# The resulting JAR (api-0.0.1-SNAPSHOT.jar) file will be created in the target directory inside the container.
RUN mvn clean package -DskipTests

# Stage 2: Create the final image to run the application

# This line specifies the base image for the final stage, which is a slim version of OpenJDK 17.
FROM openjdk:17-jdk-slim

# Sets the working directory inside the container to /app.
WORKDIR /app

# Copies the JAR file created in the build stage from the target directory to the final image’s working directory.
# The --from=build part indicates that the file should be copied from the build stage.
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /app/api.jar

# Exposes port 8000 to allow communication to/from the container.
# This informs Docker and users that the container will listen on this port.
EXPOSE 8080

# Defines the command to run the application when the container starts.
# This command runs the JAR file with java -jar api.jar.
ENTRYPOINT ["java", "-jar", "api.jar"]
