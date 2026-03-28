import axios from "axios";
import { getAccessToken } from "@/services/session";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, // For refresh token cookies
});

// Interceptor for appending access token if stored in sessionStorage (based on previous conversations)
apiClient.interceptors.request.use(
  (config) => {
    const token = getAccessToken();
    if (token) {
      config.headers.Authorization = "Bearer " + token;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// We can add response interceptor for refresh token logic later
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    // Handle 401 and refresh token logic here if needed
    return Promise.reject(error);
  }
);

export default apiClient;
