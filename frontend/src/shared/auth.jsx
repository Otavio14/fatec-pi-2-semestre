import { decodeJwt } from "jose";
import { Navigate } from "react-router-dom";

export const isAuthenticated = () => {
  const token = localStorage.getItem("token");

  if (!token || token === "") return false;

  try {
    const { exp } = decodeJwt(token);
    return exp * 1000 >= Date.now();
  } catch (error) {
    console.error("Erro ao decodificar o token:", error);
    localStorage.removeItem("token");
    return false;
  };
};

export const isAdmin = () => {
  const token = localStorage.getItem("token");

  if (!token || token === "") return false;

  const { admin } = decodeJwt(token);

  return admin;
};

export const getAuthId = () => {
  const token = localStorage.getItem("token");

  if (!token || token === "") return false;

  const { id } = decodeJwt(token);

  return id;
}

export const AdminRoute = ({ children }) => {
  if (!isAuthenticated()) {
    localStorage.removeItem("token");
    return <Navigate to="/login" />;
  }
  return children;
};
