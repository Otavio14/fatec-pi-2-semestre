import { useState } from "react"
import { EnderecoModal } from "../endereco-modal"
import { Input } from "../input"

export const ProfileInfo = ({ show, setShow, userData }) => {
    const [showEndereco, setShowEndereco] = useState(false)

    return (
        <div className={`${!!show ? "flex justify-center p-10" : "hidden"}`}>
            <div className="flex flex-col w-[25%]">
                <Input Label={"nome"} value={userData.nome} />
                <Input Label={"email"} value={userData.email} />
                <Input Label={"celular"} value={userData.celular ? userData.senha : ""} />
                <button className="border border-grey hover:bg-violet-600"
                    onClick={() => setShowEndereco(!showEndereco)}>Endereço</button>
            </div>
            <EnderecoModal
                show={showEndereco}
                setShow={setShowEndereco}
                cep={userData.endereco.cep}
                bairro={userData.endereco.bairro}
                rua={userData.endereco.rua}
                numero={userData.endereco.numero}
            />
        </div>
    )
}