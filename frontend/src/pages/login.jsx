import { useState } from "react";
import { api } from "../shared/api";
import { Input } from "../components/input.jsx";
import { NavLink, useNavigate } from "react-router-dom";
import { Swal } from "../shared/swal";

export const LoginPage = () => {
  const [Email, setEmail] = useState("");
  const [Senha, setSenha] = useState("");
  const navigate = useNavigate();

  const login = (event) => {
    event.preventDefault();

    api
      .post("/usuarios/login", { email: Email, senha: Senha })
      .then((response) => {
        localStorage.setItem("token", response.data.token);
        navigate("/admin");
      })
      .catch(() => {
        Swal.fire({
          icon: "error",
          title: "Atenção!",
          text: "Email ou senha incorretos",
        });
      });
  };

  return (
    <div className="flex h-full min-h-screen w-full min-w-full flex-col items-center justify-center bg-[#0c2d57b3]">
      <form
        onSubmit={login}
        className="relative flex w-[500px] flex-col justify-center rounded-[10px] bg-white px-[65px] pb-[60px] pt-[33px]"
      >
        <NavLink
          to="/"
          className="absolute right-[20px] top-[15px] text-[#8f9eb2]"
        >
          Voltar para a Home
        </NavLink>
        <h2 className="mb-[20px] text-[30px] font-semibold leading-[140%] text-[#0c2d57]">
          Log in
        </h2>
        <Input
          Label={"Email"}
          value={Email}
          placeholder={"Digite seu email"}
          onChange={(event) => setEmail(event.target.value)}
          required
          type="email"
          autoComplete="email"
        />
        <Input
          Label={"Senha"}
          value={Senha}
          placeholder={"*********"}
          onChange={(event) => setSenha(event.target.value)}
          required
          type="password"
          autoComplete="password"
        />
        <button
          style={{ transition: "color .3s, background-color .5s" }}
          className="mt-[14px] rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[22px] text-white hover:bg-white hover:text-[#0c2d57]"
        >
          Entrar
        </button>
      </form>
    </div>
  );
};
