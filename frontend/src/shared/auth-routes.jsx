import { Navigate } from "react-router-dom";
import { isAdmin, isAuthenticated } from "./auth";

export const ClienteRoute = ({ children }) => {
  if (!isAuthenticated()) {
    localStorage.removeItem("token");
    return <Navigate to="/login" />;
  }
  return children;
};

export const AdminRoute = ({ children }) => {
  if (!isAuthenticated() || !isAdmin()) {
    localStorage.removeItem("token");
    return <Navigate to="/login" />;
  }
  return children;
};
