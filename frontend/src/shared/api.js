import axios from "axios";

export const api = axios.create({
  baseURL: window.location.hostname.includes("localhost")
    ? "http://localhost:8080"
    : "https://fatec-pi-2-semestre.onrender.com/api/",
    // : "http://localhost:3000/api/",
});
