import { useEffect, useState } from "react";
import { api } from "../shared/api";
import { Input } from "../components/input.jsx";
import { NavLink, useNavigate } from "react-router-dom";
import { Swal } from "../shared/swal";
import { isAdmin, isAuthenticated } from "../shared/auth.jsx";

export const LoginPage = () => {
  const [Email, setEmail] = useState("");
  const [Senha, setSenha] = useState("");
  const Navigate = useNavigate();

  const login = (event) => {
    event.preventDefault();

    api
      .post("/usuarios/login", { email: Email, senha: Senha })
      .then((response) => {
        if (response.data == "Dados inválidos!") {
          Swal.fire({
            icon: "error",
            title: "Atenção!",
            text: "Email ou senha incorretos",
          });
          return
        }
        localStorage.setItem("token", response.data);
        isAuthenticated() ? isAdmin() ? Navigate("/admin") : Navigate("/") :
          Swal.fire({
            icon: "error",
            title: "Atenção!",
            text: "Email ou senha incorretos",
          })
      })
      .catch((error) => {
        Swal.fire({
          icon: "warning",
          title: "Atenção!",
          text: error?.response?.data || "Email ou senha incorretos",
        });
      });
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) Navigate("/admin");
  }, [Navigate]);

  return (
    <div className="flex h-full min-h-screen w-full min-w-full flex-col items-center justify-center bg-gradient-to-r from-[#0c2d57] via-[#0c2d57b3] to-[#1a3a65] p-4">
      <NavLink
        to="/"
        className="absolute left-4 top-4 flex items-center justify-center rounded-full bg-white p-2 shadow-lg transition-all duration-300 hover:bg-[#dd3842] hover:text-white hover:-translate-x-1"
      >
        <span className="font-medium">Voltar</span>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="currentColor"
          viewBox="0 0 24 24"
          className="h-6 w-6"
        >
          <path
            d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z"
          />
        </svg>
      </NavLink>

      <form
        onSubmit={login}
        className="relative flex w-full max-w-[400px] flex-col justify-center rounded-xl bg-white px-6 py-8 shadow-lg sm:px-10"
      >
        <h2 className="mb-6 text-center text-[28px] font-bold leading-[140%] text-[#0c2d57] sm:text-[32px]">
          Bem-vindo!
        </h2>
        <Input
          Label={"Email"}
          value={Email}
          placeholder={"Digite seu email"}
          onChange={(event) => setEmail(event.target.value)}
          required
          type="email"
          autoComplete="email"
          className="mb-4"
        />
        <Input
          Label={"Senha"}
          value={Senha}
          placeholder={"*********"}
          onChange={(event) => setSenha(event.target.value)}
          required
          type="password"
          autoComplete="password"
          className="mb-6"
        />
        <button
          style={{ transition: "all 0.3s ease-in-out" }}
          className="w-full rounded-lg bg-[#dd3842] px-6 py-3 text-lg font-semibold text-white shadow-md hover:bg-[#c32f39] hover:shadow-lg"
        >
          Entrar
        </button>
        <div className="mt-4 text-center text-sm text-[#8f9eb2]">
          Ainda não tem uma conta?{" "}
          <NavLink
            to="/cadastro"
            className="text-[#dd3842] hover:underline hover:text-[#c32f39] transition-colors"
          >
            Cadastre-se
          </NavLink>
        </div>
      </form>
    </div>

  );
};
