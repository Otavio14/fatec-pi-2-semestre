import React, { useEffect, useState } from "react";
import { ProfileInfo } from "../components/profile-modals/profile-info";
import { ProfilePedidos } from "../components/profile-modals/profile-pedidos";
import { api } from "../shared/api";

export const ProfilePage = () => {
    const [showProfile, setShowProfile] = useState(true)
    const [show, setShow] = useState(false)
    const [showPedidos, setShowPedidos] = useState(false);

    const userData = {
        nome: "João Manoel",
        email: "JMzinhoBalaForte@mail.com",
        celular: "",
        endereco: {
            cep: "13345-423",
            bairro: "Pinda Majuba",
            rua: "Brits Augusto",
            numero: "392",
        },
    }

    useEffect(() => {
        api.get(`/clientes/${1}`).then((res) => {
            console.log({ress: res.data})
        }).catch((err) => {
            console.log({erro: err})
        })
    }, []);

    function SwitchModals(modalNumber){
        if(modalNumber == 1){
            setShowProfile(true)
            setShowPedidos(false)
        }
        if(modalNumber == 2){
            setShowPedidos(true)
            setShowProfile(false)
        }
    }

    const AsideButton = ({ nome, evento }) => {
        return (
            <button className="shadow-md hover:bg-violet-600"
                onClick={evento}>
                {nome}
            </button>
        )
    }

    return (
        <div className="flex flex-row shadow-lg w-3/4 height-fit place-self-center mt-5">
            <div className="flex flex-col shadow-md height-fit w-1/4">
                <AsideButton nome={"Meu perfil"} evento={() => SwitchModals(1)} />
                <AsideButton nome={"Meus pedidos"} evento={() => SwitchModals(2)}/>
            </div>
            <div className="flex flex-col shadow-md w-3/4">
                <ProfileInfo show={showProfile} setShow={setShowProfile} userData={userData}/>
                <ProfilePedidos show={showPedidos} setShow={setShowPedidos} />
            </div>
        </div>
    )
}