import { useState } from "react"
import { EnderecoModal } from "../endereco-modal"
import { Input } from "../input"

export const ProfileInfo = ({ show, setShow, userData }) => {
    const [showEndereco, setShowEndereco] = useState(false)

    return (
        <div className={`${!!show ? "flex justify-center" : "hidden"}`}>
            <div className="flex flex-col w-full">
                <div className="shadow-md bg-gray-100 p-6 rounded-lg">
                    <Input Label={"nome"} value={userData.nome} />
                    <Input Label={"email"} value={userData.email} />
                    <Input Label={"celular"} value={userData.telefone ? userData.telefone : ""} />
                </div>
                <button className="mt-6 px-6 py-2 bg-blue-500 text-white font-semibold rounded-xl shadow-lg hover:bg-blue-600 transition-all duration-200"
                    onClick={() => setShowEndereco(!showEndereco)}>Endereço</button>
            </div>
            <EnderecoModal
                show={showEndereco}
                setShow={setShowEndereco}
                cep={userData.cep}
                bairro={userData.bairro}
                rua={userData.endereco}
                numero={userData.numero}
            />
        </div>
    )
}