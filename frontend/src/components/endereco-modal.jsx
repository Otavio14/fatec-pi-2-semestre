import React from "react";
import { Input } from "./input";

export const EnderecoModal = ({ show, setShow, cep, bairro, rua, numero }) => {

    if (show == false) {
        return
    }

    return (
        <div className="flex fixed inset-0 justify-center items-center bg-black bg-opacity-50">
            <div className="flex flex-col w-[25%] bg-white border-2 border-black p-10 rounded-2xl">
                <button className="border-2 border-black rounded-full px-2 w-fit hover:bg-red-700" onClick={() => setShow(!show)}>X</button>
                <Input Label={"CEP"} value={cep} />
                <Input Label={"Bairro"} value={bairro} />
                <Input Label={"Rua"} value={rua} />
                <Input Label={"Numero"} value={numero} />
            </div>
        </div>
    )
} 