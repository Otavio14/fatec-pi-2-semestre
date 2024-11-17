import React, { useEffect, useState } from "react";
import { ProfileInfo } from "../components/profile-modals/profile-info";
import { ProfilePedidos } from "../components/profile-modals/profile-pedidos";
import { api } from "../shared/api";
import { getAuthId, isAuthenticated } from "../shared/auth";
import { useNavigate } from "react-router-dom";

export const ProfilePage = ({ }) => {
    const [showProfile, setShowProfile] = useState(true)
    const [show, setShow] = useState(false)
    const [showPedidos, setShowPedidos] = useState(false);
    const Navigate = useNavigate()

    useEffect(() => {
        if (isAuthenticated() == false) {
            Navigate("/home")
            localStorage.removeItem("token")
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
    }

    const [usuario, setUsuario] = useState(userData)
    const [pedidos, setPedidos] = useState([])

    const id = getAuthId()

    useEffect(() => {
        api.get(`/clientes/${id}`).then((res) => {
            console.log({ resProfile: res.data })
            setUsuario((prev) => {
                return { ...prev, ...res.data }
            })
        }).catch((err) => {
            console.log({ erroProfile: err })
        })
        api.get(`/pedidos/cliente/${id}`).then((res) => {
            console.log({ pedidos: res })
            setPedidos(res.data)
        }).catch((err) => {
            console.log({ erroPedidos: err })
        })
    }, []);

    function SwitchModals(modalNumber) {
        if (modalNumber == 1) {
            setShowProfile(true)
            setShowPedidos(false)
        }
        if (modalNumber == 2) {
            setShowPedidos(true)
            setShowProfile(false)
        }
    }

    function logOut() {
        localStorage.removeItem("token")
        Navigate("/")
    }

    return (

        <div className="flex justify-center min-h-screen bg-gradient-to-r from-blue-100 via-blue-50 to-blue-100 p-5">
            <div className="flex flex-row shadow-2xl rounded-xl bg-white w-3/4 h-fit">
                <div className="flex flex-col bg-gradient-to-b from-blue-500 to-blue-700 text-white w-1/4 rounded-l-xl p-5">
                    <h2 className="text-lg font-bold mb-4 text-center">Minha Conta</h2>
                    <button
                        className="mb-3 px-4 py-2 bg-blue-400 hover:bg-blue-300 rounded-lg transition-colors"
                        onClick={() => SwitchModals(1)}
                    >
                        Meu Perfil
                    </button>
                    <button
                        className="mb-3 px-4 py-2 bg-blue-400 hover:bg-blue-300 rounded-lg transition-colors"
                        onClick={() => SwitchModals(2)}
                    >
                        Meus Pedidos
                    </button>
                    <button
                        className="mt-auto px-4 py-2 bg-red-500 hover:bg-red-400 rounded-lg transition-colors"
                        onClick={logOut}
                    >
                        Sair
                    </button>
                </div>

                {/* Main Content */}
                <div className="flex flex-col w-3/4 p-8 rounded-r-xl">
                    <ProfileInfo show={showProfile} setShow={setShowProfile} userData={usuario} />
                    <ProfilePedidos show={showPedidos} setShow={setShowPedidos} pedidos={pedidos} />
                </div>
            </div>
        </div>
    )
}