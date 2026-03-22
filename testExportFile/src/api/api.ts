import axios from "axios";

const BASE_URL = "http://localhost:8080/api/v1";

export const api = axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-Type": "application/json",
    }
});

api.interceptors.request.use(
    (config) => {
        config.headers["Authorization"] = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2aXJhY2hhbXJldW5uQGdtYWlsLmNvbSIsImlhdCI6MTc3NDE4ODU5MywiZXhwIjoxNzc0Mjc0OTkzfQ.A92_NJtQ96gqZAqllwcCQEv1W6--Zw4o6Ck_kmGE55Qe0GpAc9MEk2kOVIAZ6hzn"
        return config;
    }, (err) => {
        return new Promise(err);
    }
);