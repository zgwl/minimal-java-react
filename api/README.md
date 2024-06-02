## VSCode Java Environment Setup

### Install JDK

Download JDK from the [Oracle Official Page](https://www.oracle.com/java/technologies/downloads). Version 17 or 21 are recommended. Check the Java version to make sure it's installed correctly.

```shell
java --version
```

### Setup VS Code

Install the following VSCode Extension

- [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) for basic Java support.

- [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack) for Spring Boot support.

- (Optional) [Spring Initializr Java Support](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-initializr) for quickly initializing a new Java Spring project. You could also use the [https://start.spring.io/](https://start.spring.io/) to manually initialize the project.

Enable Java Auto Import in VS Code setting by searching "java import save" in the VS Code setting page.

### Generate Project

Shift + Command + P to invoke the command paletter in VSCode, type **Spring Initializr** and follow the instruction to setup the project.

### Run Project

#### From VS Code

Right-click the Main Java file and select **Run Java**

If getting a **.java is not on the classpath of project** error, do the following:

Press "Ctrl + Shift + P", choose "java : Clean java Language Server workspace".

#### From Command Line

Go to the project root directory, and run the following command

```shell
./mvnw spring-boot:run
```

### Setup Supabase Database

- Signup in [Supabase](https://supabase.com/) and create a new project.

- Once the project gets created, go to the **Setting** by clicking the Gear icon from the left navigation bar.

- Click **JDBC** and find the connection string.

- Copy the connection string to the **application.properties**, and save the URL, User, and Password seperately.

## Backend CI / CD

Install the [docker](https://docs.docker.com/engine/install/) and make sure the `docker --version` command can return a valid version.

### Build the Backend Docker Image

Go to the **api** directory.

```shell
docker build -t ai-docker-api .
```

### Run the Backend Docker Container

```shell
docker run -p 8080:8080 ai-docker-api
```

Using postman to verify the API works as expected.
