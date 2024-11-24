import React, { useEffect, useState } from "react";
import { Input } from "./input";
import { api } from "../shared/api";
import { Select } from "./select";
import axios from "axios";
import Swal from "sweetalert2";

export const EnderecoModal = ({ show, setShow, hasChanged, setHasChanged, cep, bairro, rua, numero, estado, idCidade, updateFieldData, confirmChange }) => {
    const [Estados, setEstados] = useState([]);
    const [Cidades, setCidades] = useState([]);

    const [Cep, setCep] = useState("");
    const [Numero, setNumero] = useState(0);
    const [Estado, setEstado] = useState(0);
    const [Cidade, setCidade] = useState(0);
    const [Endereco, setEndereco] = useState("");
    const [Bairro, setBairro] = useState("");
    const [NomeCidade, setNomeCidade] = useState("");

    useEffect(() => {
        api.get("/estados").then((response) => {
            setEstados(response.data);
        })
    }, []);

    useEffect(() => {
        if (!Estado) return;

        api.get(`/cidades/estado/${Estado}`).then((response) => {
            setCidades(response.data);
            setCidade(response.data.find((cidade) => cidade.nome === NomeCidade).id);
        });
    }, [Estado, NomeCidade]);

    useEffect(() => {
        updateFieldData("idCidade", Cidade)
    }, [Cidade]);

    useEffect(() => {
        if (Cep.length !== 9) return;

        axios.get(`https://viacep.com.br/ws/${Cep}/json/`).then((response) => {
            if (response.data.erro) return;

            console.log({ viacep: response })

            const { logradouro, bairro, localidade, uf } = response.data;
            setEndereco(logradouro);
            setBairro(bairro);
            setEstado(Estados.find((estado) => estado.sigla === uf).id);
            setNomeCidade(localidade);
        });
    }, [Cep, Estados]);

    return (
        <div
            className={`${show ? "flex fixed inset-0 justify-center items-center bg-black bg-opacity-30 backdrop-blur-md transition-opacity duration-300" : "hidden"}`}
        >
            <div className="flex flex-col w-[30%] bg-white shadow-xl p-8 rounded-3xl relative">
                <button
                    className="absolute top-4 right-4 text-white bg-red-500 hover:bg-red-600 px-4 py-1 rounded-full shadow-lg transition-all duration-200"
                    onClick={() => setShow(!show)}
                >
                    X
                </button>

                <h2 className="text-2xl font-bold mb-6 text-center text-gray-700">Endereço</h2>
                <Input
                    Label={"CEP"}
                    value={cep || ""}
                    placeholder="00000-000"
                    onChange={(e) => {
                        updateFieldData("cep", e.target.value.replace(/\D/g, "")
                            .replace(/(\d{5})(\d)/, "$1-$2")
                            .slice(0, 9),)
                        setCep(e.target.value)
                    }}
                    required
                    type="text"
                    autoComplete="text"
                />
                <Select
                    Label={"Estado"}
                    onChange={(e) => {
                        setEstado(e.target.value)
                    }}
                    value={Estado}
                >
                    <option>Selecione o estado</option>
                    {Estados.map((estado) => (
                        <option key={estado.id} value={estado.id}>
                            {estado.sigla} - {estado.nome}
                        </option>
                    ))}
                </Select>
                <Select
                    Label={"Cidade"}
                    onChange={(e) => updateFieldData("idCidade", e.target.value)}
                    value={idCidade ? idCidade : Cidade}
                >
                    <option>Selecione a cidade</option>
                    {Cidades.map((cidade) => (
                        <option key={cidade.id} value={cidade.id}>
                            {cidade.nome}
                        </option>
                    ))}
                </Select>
                <Input
                    Label={"Bairro"}
                    value={bairro || ""}
                    placeholder={"Digite seu bairro"}
                    onChange={(e) => updateFieldData("bairro", e.target.value)}
                    required
                    type="text"
                    autoComplete="text"
                />
                <Input
                    Label={"Rua"}
                    value={rua}
                    placeholder={"Digite sua rua"}
                    onChange={(e) => updateFieldData("endereco", e.target.value)}
                    required
                    type="text"
                    autoComplete="text"
                />
                <Input
                    Label={"Número"}
                    value={numero || ""}
                    placeholder={"Digite seu número"}
                    onChange={(e) => updateFieldData("numero", parseInt(e.target.value))}
                    required
                    type="text"
                    autoComplete="text"
                />
                <button
                    className="mt-6 px-6 py-2 bg-blue-500 text-white font-semibold rounded-xl shadow-lg hover:bg-blue-600 transition-all duration-200"
                    disabled={!hasChanged}
                    onClick={() => {
                        if (confirmChange()) {
                            Swal.fire({
                                icon: "error",
                                title: "Erro!",
                                text: "Algo deu errado!",
                            });
                        } else {
                            Swal.fire({
                                icon: "success",
                                title: "Alterado!",
                                text: "Informações alteradas!",
                            });
                            setShow(!show);
                            setHasChanged(false)
                        }
                    }}
                >
                    Salvar
                </button>

            </div>
        </div >
    );
};
