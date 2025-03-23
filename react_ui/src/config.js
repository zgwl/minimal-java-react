const DEFAULT_API_BASE_URL = "https://minimal-java-api.fly.dev";
const ENVIRONMENT_API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const API_BASE_URL = ENVIRONMENT_API_BASE_URL || DEFAULT_API_BASE_URL;

export const API_URLS = {
  auth: `${API_BASE_URL}/auth`,
  api: `${API_BASE_URL}/api`,
};
