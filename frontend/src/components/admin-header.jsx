import { List, SignOut } from "@phosphor-icons/react";
import { useState } from "react";
import { NavLink, useNavigate, Outlet } from "react-router-dom";
import Icone from "../assets/icone-2.png";

export const AdminHeader = () => {
  const Navigate = useNavigate();
  const [OpenMenu, setOpenMenu] = useState(false);

  const logout = () => {
    localStorage.removeItem("token");
    Navigate("/login");
  };

  return (
    <div className="flex flex-col overflow-hidden md:grid md:grid-cols-[200px,1fr]">
      {/* Desktop */}
      <div className="hidden h-screen w-[200px] flex-col gap-8 bg-[#D3D0CB] p-4 text-white md:flex">
        <div className="w-full p-4">
          <img className="object-fit h-full" src={Icone} />
        </div>
        <div className="flex flex-col gap-4 font-semibold text-[#0C2D57]">
          <NavLink to="/admin">Home</NavLink>
          <NavLink to="/admin/avaliacoes">Avaliações</NavLink>
          <NavLink to="/admin/categorias">Categorias</NavLink>
          <NavLink to="/admin/clientes">Clientes</NavLink>
          <NavLink to="/admin/cupons">Cupons</NavLink>
          <NavLink to="/admin/pedidos">Pedidos</NavLink>
          <NavLink to="/admin/fornecedores">Fornecedores</NavLink>
          <NavLink to="/admin/fornecedores-produtos">
            Fornecedores Produtos
          </NavLink>
          <NavLink to="/admin/produtos">Produtos</NavLink>
          <NavLink to="/admin/produtos-categorias">Produtos Categorias</NavLink>
          <NavLink to="/admin/usuarios">Usuários</NavLink>
        </div>
        <button
          onClick={logout}
          className="mx-auto mt-auto w-fit rounded bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white"
        >
          Sair
        </button>
      </div>
      {/* Mobile */}
      <div className="relative grid h-[123px] w-screen grid-cols-3 items-center bg-[#2b38d1] px-4 py-8 text-white md:hidden">
        <button
          className="justify-self-start px-4"
          onClick={() => {
            setOpenMenu((o) => !o);
          }}
        >
          <List size={32} />
        </button>
        <div className="flex flex-col items-center justify-self-center">
          <img
            alt="Logo da empresa"
            src="https://cdn-icons-png.flaticon.com/512/2964/2964514.png"
            className="m-0 mx-4 h-12 w-12 object-contain"
          />
          <p>Suplementos</p>
        </div>
        <button onClick={logout} className="justify-self-end px-4 text-white">
          <SignOut size={32} />
        </button>
        <div
          style={{ transition: "max-height 0.5s, padding 0.5s" }}
          className={`absolute top-full flex ${OpenMenu ? "max-h-[200vh] overflow-auto py-8" : "max-h-0 overflow-hidden py-0"} h-fit w-screen flex-col items-center gap-8 bg-[#2b38d1] text-white`}
        >
          <NavLink to="/admin">Home</NavLink>
          <NavLink to="/admin/avaliacoes">Avaliações</NavLink>
          <NavLink to="/admin/categorias">Categorias</NavLink>
          <NavLink to="/admin/clientes">Clientes</NavLink>
          <NavLink to="/admin/cupons">Cupons</NavLink>
          <NavLink to="/admin/pedidos">Pedidos</NavLink>
          <NavLink to="/admin/fornecedores">Fornecedores</NavLink>
          <NavLink to="/admin/fornecedores-produtos">
            Fornecedores Produtos
          </NavLink>
          <NavLink to="/admin/produtos">Produtos</NavLink>
          <NavLink to="/admin/produtos-categorias">Produtos Categorias</NavLink>
          <NavLink to="/admin/usuarios">Usuários</NavLink>
        </div>
      </div>
      <Outlet />
    </div>
  );
};
