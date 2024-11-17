import React from "react";
import { Input } from "../input";

export const Step2 = ({ data, updateFieldData }) => {

    return (
        <>
            <Input
                Label={"CEP"}
                value={data.cep || ""}
                placeholder={"Digite seu CEP"}
                onChange={(e) => updateFieldData("cep", e.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Estado"}
                value={data.estado || ""}
                placeholder={"Digite seu Estado"}
                onChange={(e) => updateFieldData("estado", e.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Cidade"}
                value={data.cidade || ""}
                placeholder={"Digite sua cidade"}
                onChange={(e) => updateFieldData("cidade", e.target.value)}
                required
                type="text"
                autoComplete="text"
            />
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