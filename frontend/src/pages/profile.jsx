import React, { useState } from "react";
import { Input } from "../components/input";
import { EnderecoModal } from "../components/endereco-modal.jsx";

export const ProfilePage = () => {

    const [show, setShow] = useState(false)

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

    return (
        <div className="flex justify-center">
            <div className="flex flex-col w-[25%]">
                <Input Label={"nome"} value={userData.nome} />
                <Input Label={"email"} value={userData.email} />
                <Input Label={"celular"} value={userData.celular ? userData.senha : "Adicionar"} />
                <button className="border-2 border-black" onClick={() => setShow(!show)}>Endereço</button>
            </div>
            <EnderecoModal
                show={show}
                setShow={setShow}
                cep={userData.endereco.cep}
                bairro={userData.endereco.bairro}
                rua={userData.endereco.rua}
                numero={userData.endereco.numero}
                 />
        </div>
    )
}