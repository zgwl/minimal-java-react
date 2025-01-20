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

### Setup Local Environment Variable

Create a `.env` file under the `api` directory, add `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` accordingly.

Export the variable into the shell before running the application:

```shell
export $(grep -v '^#' .env | xargs)
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
docker run -p 8080:8080 --env-file .env ai-docker-api
```

Using postman to verify the API works as expected.

### AWS Access Key Setup

- Signup for AWS if you don't have an account.

- Go to the IAM Dashboard.

- Navigate to the **Users** section.

- Click **Create User**.

- Set a meaningful user name.

- In the next step, select **Attach policies directly**, and grant the issue with following permissions:

  - AWSLambda_FullAccess
  - AmazonEC2ContainerRegistryFullAccess

- Click the user once it's created, go to the user home page, then click the **Create access key** under the Summary section.

- Select **Application running outside AWS** as the user case.

- Set a meaningful description tag (optional).

- Save the generated **Access key** and **Secret access key** for future reference.

### AWS ECR Setup

- Go to the AWS ECR console (we're using **us-west-2** region, so make sure the region is correct)

- Click **Create a repository**.

- Set the Visibility to **Private** and setup a meaningful repository name.

- Repository URI can be found from the repository detail page, it will be in the format **account-id.dkr.ecr.region.amazonaws.com/repository-name**

### Github Repo Setup

- Go to Github Repository

- Click Settings -> Secrets -> Actions

- Add the following secrets under Repository secrets:

  - **AWS_ACCESS_KEY_ID**: from AWS setup above
  - **AWS_SECRET_ACCESS_KEY**: from AWS setup above
  - **ECR_REPOSITORY_URI**: from AWS setup above

### Setup the Fly.io

- Install the [flyctl](https://fly.io/docs/hands-on/install-flyctl/).

- Sign in with Fly.io

```shell
fly auth login
```

- From the app directory, create a fly.io app (it may ask you to pay $5 per month)

```shell
fly launch
```

- From the app directory, deploy the app to fly.io

```shell
fly deploy
```

- verify the deployed API at https://**[deployed_name_in_fly_io]**.fly.dev/api/users

- Create a new access token in [Fly.io](https://fly.io/user/personal_access_tokens) and add it to the Github Repository secrets:

  - **FLY_API_TOKEN**: from Fly.io
