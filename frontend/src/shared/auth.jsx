import { decodeJwt } from "jose";
import { Navigate } from "react-router-dom";

const isAuthenticated = () => {
  const token = localStorage.getItem("token");

  if (!token || token === "") return false;

  const { exp } = decodeJwt(token);

  return exp * 1000 >= Date.now();
};

export const AdminRoute = ({ children }) => {
  if (!isAuthenticated()) {
    localStorage.removeItem("token");
    return <Navigate to="/login" />;
  }
  return children;
};
