import axios from "axios";

const api = axios.create({
  baseURL: window.location.hostname.includes("localhost")
    ? "http://localhost:8080"
    : "https://fatec-pi-2-semestre.onrender.com/api/",
  // : "http://localhost:3000/api/",
  // : "http://localhost:3000/api/",
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
