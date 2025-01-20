import { API_URLS } from "../config";

export const sendMessage = async (prompt, sessionId = null) => {
  const token = localStorage.getItem("token");
  const url = new URL(`${API_URLS.api}/chat`);
  url.searchParams.append("prompt", prompt);
  if (sessionId) {
    url.searchParams.append("sessionId", sessionId);
  }

  const response = await fetch(url, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to send message");
  }

  return response.json();
};

export const getDiagnosisRecords = async () => {
  const token = localStorage.getItem("token");
  const response = await fetch(`${API_URLS.api}/diagnosis-records`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to fetch records");
  }

  return response.json();
};
