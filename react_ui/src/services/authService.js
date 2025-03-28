import { API_URLS } from "../config";

export const login = async (email, password) => {
  const response = await fetch(`${API_URLS.auth}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    throw new Error("Login failed");
  }

  const data = await response.json();
  return data;
};

export const logout = async () => {
  await fetch(`${API_URLS.auth}/logout`, {
    method: "POST",
    credentials: "include",
  });
};

export const isAuthenticated = async () => {
  try {
    const response = await fetch(`${API_URLS.api}/auth-check`, {
      credentials: "include",
    });
    return response.ok;
  } catch (error) {
    return false;
  }
};
