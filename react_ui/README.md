# Setup the React Frontend

## Install Node.js

Follow the [instruction](https://nodejs.org/en/download/package-manager) to download and install Node.js (V20+ Version).

## Setup React Project

Replace the _react_ui_ with the desired project name.

```bash
npx create-react-app react_ui
```

Launch the React frontend locally from the react project directory.

```bash
npm run start
```

## Frontend CI / CD

### Build the Frontend Docker Image

Go to the **react_ui** directory.

```shell
docker build -t ai-docker-frontend .
```

### Run the Frontend Docker Image

```shell
docker run -p 80:80 ai-docker-frontend
```

### Setup the Fly.io

- Install the [flyctl](https://fly.io/docs/hands-on/install-flyctl/).

- Sign in with Fly.io

```shell
fly auth login
```

- From the react_ui directory, create a fly.io app (it may ask you to pay $5 per month)

```shell
fly launch
```

- From the react_ui directory, deploy the app to fly.io

```shell
fly deploy
```

- verify the deployed API at https://**[deployed_name_in_fly_io]**.fly.dev/api/users

- Create a new access token in [Fly.io](https://fly.io/user/personal_access_tokens) and add it to the Github Repository secrets:

  - **FLY_API_TOKEN**: from Fly.io

## Local Development

Point the React app to the local backend by setting the `REACT_APP_API_BASE_URL` environment variable to `http://localhost:8080`.

```shell
REACT_APP_API_BASE_URL=http://localhost:8080 npm run start
```

By default, the React app will point to the deployed backend at https://minimal-java-api.fly.dev.

```shell
npm run start
```
