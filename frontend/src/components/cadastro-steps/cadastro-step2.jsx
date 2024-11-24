import React, { useEffect, useState } from "react";
import { Input } from "../input";
import { Select } from "../select";
import { api } from "../../shared/api";
import axios from "axios";

export const Step2 = ({ data, updateFieldData }) => {

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
        <>
            <Input
                Label={"CEP"}
                value={data.cep || ""}
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
                value={data?.idCidade ? data.idCidade : Cidade}
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
                value={data.bairro || ""}
                placeholder={"Digite seu bairro"}
                onChange={(e) => updateFieldData("bairro", e.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Rua"}
                value={data.endereco || ""}
                placeholder={"Digite sua rua"}
                onChange={(e) => updateFieldData("endereco", e.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Número"}
                value={data.numero || ""}
                placeholder={"Digite seu número"}
                onChange={(e) => updateFieldData("numero", parseInt(e.target.value))}
                required
                type="text"
                autoComplete="text"
            />
        </>
    )
}