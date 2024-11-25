import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ProfileInfo } from "../components/profile-modals/profile-info";
import { ProfilePedidos } from "../components/profile-modals/profile-pedidos";
import { api } from "../shared/api";
import { getAuthId, isAuthenticated } from "../shared/auth";

export const ProfilePage = () => {
  const [showProfile, setShowProfile] = useState(true);
  const [showPedidos, setShowPedidos] = useState(false);
  const Navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated() == false) {
      Navigate("/home");
      localStorage.removeItem("token");
    }
  }, []);

  const userData = {
    bairro: "",
    cep: "",
    email: "",
    endereco: "",
    id: "",
    idCidade: "",
    nome: "",
    numero: "",
    telefone: "",
  };

  const [usuario, setUsuario] = useState(userData);
  const [pedidos, setPedidos] = useState([]);
  const [hasChanged, sethasChanged] = useState(false);

  function AlteraUsuario(name, value) {
    setUsuario((prev) => {
      return { ...prev, [name]: value };
    });
    sethasChanged(true);
  }

  function confirmChange() {
    api
      .put(`/clientes/${id}`, { ...usuario })
      .then(() => {
        return true;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
  }

  const id = getAuthId();

  useEffect(() => {
    api
      .get(`/clientes/${id}`)
      .then((res) => {
        setUsuario((prev) => {
          return { ...prev, ...res.data };
        });
      })
      .catch((err) => {
        console.log({ erroProfile: err });
      });
    api
      .get(`/pedidos/cliente/${id}`)
      .then((res) => {
        setPedidos(res.data);
      })
      .catch((err) => {
        console.log({ erroPedidos: err });
      });
  }, [id]);

  function SwitchModals(modalNumber) {
    if (modalNumber == 1) {
      setShowProfile(true);
      setShowPedidos(false);
    }
    if (modalNumber == 2) {
      setShowPedidos(true);
      setShowProfile(false);
    }
  }

  function logOut() {
    localStorage.removeItem("token");
    Navigate("/");
  }

  return (
    <div className="flex min-h-screen justify-center bg-gradient-to-r from-blue-100 via-blue-50 to-blue-100 p-2 sm:p-5">
      <div className="flex h-fit w-full max-w-[800px] flex-col rounded-xl bg-white shadow-2xl sm:w-3/4 sm:flex-row">
        <div className="flex w-full flex-row flex-wrap items-center justify-center gap-4 rounded-t-xl bg-gradient-to-b from-blue-500 to-blue-700 p-5 text-white sm:w-1/4 sm:flex-col sm:justify-start sm:rounded-l-xl sm:rounded-r-none">
          <h2 className="text-center text-lg font-bold">Minha Conta</h2>
          <button
            className="rounded-lg bg-blue-400 px-4 py-2 transition-colors hover:bg-blue-300"
            onClick={() => SwitchModals(1)}
          >
            Meu Perfil
          </button>
          <button
            className="rounded-lg bg-blue-400 px-4 py-2 transition-colors hover:bg-blue-300"
            onClick={() => SwitchModals(2)}
          >
            Meus Pedidos
          </button>
          <button
            className="mt-auto rounded-lg bg-red-500 px-4 py-2 transition-colors hover:bg-red-400"
            onClick={logOut}
          >
            Sair
          </button>
        </div>

        <div className="flex w-full flex-col rounded-r-xl p-8 sm:w-3/4">
          <ProfileInfo
            show={showProfile}
            setShow={setShowProfile}
            userData={usuario}
            handleSalvar={AlteraUsuario}
            setHasChanged={sethasChanged}
            hasChanged={hasChanged}
            confirmChange={confirmChange}
          />
          <ProfilePedidos
            show={showPedidos}
            setShow={setShowPedidos}
            pedidos={pedidos}
          />
        </div>
      </div>
    </div>
  );
};
