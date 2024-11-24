import axios from "axios";

const api = axios.create({
  baseURL: window.location.hostname.includes("localhost")
    ? "http://localhost:8080"
    : "https://fobov-java.onrender.com",
});

api.interceptors.request.use(
  function (config) {
    config.headers.Authorization = localStorage.getItem("token")
      ? "Bearer " + localStorage.getItem("token")
      : undefined;
    return config;
  },
  function (error) {
    return Promise.reject(error);
  },
);

export { api };
