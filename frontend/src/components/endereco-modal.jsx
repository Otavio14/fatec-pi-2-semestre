import React from "react";
import { Input } from "./input";

export const EnderecoModal = ({ show, setShow, cep, bairro, rua, numero }) => {
    return (
        <div
            className={`${show ? "flex fixed inset-0 justify-center items-center bg-black bg-opacity-30 backdrop-blur-md transition-opacity duration-300" : "hidden"}`}
        >
            {/* Modal Container */}
            <div className="flex flex-col w-[30%] bg-white shadow-xl p-8 rounded-3xl relative">
                {/* Close Button */}
                <button
                    className="absolute top-4 right-4 text-white bg-red-500 hover:bg-red-600 px-4 py-1 rounded-full shadow-lg transition-all duration-200"
                    onClick={() => setShow(!show)}
                >
                    X
                </button>

                <h2 className="text-2xl font-bold mb-6 text-center text-gray-700">Endereço</h2>
                
                {/* Inputs */}
                <Input Label={"CEP"} value={cep} />
                <Input Label={"Bairro"} value={bairro} />
                <Input Label={"Rua"} value={rua} />
                <Input Label={"Número"} value={numero} />

                {/* Save Button */}
                <button
                    className="mt-6 px-6 py-2 bg-blue-500 text-white font-semibold rounded-xl shadow-lg hover:bg-blue-600 transition-all duration-200"
                    onClick={() => alert("Endereço salvo!")}
                >
                    Salvar
                </button>
            </div>
        </div>
    );
};
