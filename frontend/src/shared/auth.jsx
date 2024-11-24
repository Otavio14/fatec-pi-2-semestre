import { decodeJwt } from "jose";

export const isAuthenticated = () => {
  const token = localStorage.getItem("token");

  if (!token || token === "") return false;

  try {
    const { exp } = decodeJwt(token);
    return exp * 1000 >= Date.now();
  } catch (error) {
    localStorage.removeItem("token");
    return false;
  }
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
};
