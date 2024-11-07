import React from "react";
import { Input } from "../input";

export const Step2 = ({ data, dataChanger }) => {

    return (
        <>
            <Input
                Label={"CEP"}
                value={data.cep}
                placeholder={"Digite seu CEP"}
                onChange={(event) => dataChanger("cep", event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Estado"}
                value={data.estado}
                placeholder={"Digite seu Estado"}
                onChange={(event) => dataChanger("estado", event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Cidade"}
                value={data.cidade}
                placeholder={"Digite sua cidade"}
                onChange={(event) => dataChanger("cidade", event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Bairro"}
                value={data.bairro}
                placeholder={"Digite seu bairro"}
                onChange={(event) => dataChanger("bairro", event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Rua"}
                value={data.endereco}
                placeholder={"Digite sua rua"}
                onChange={(event) => dataChanger("endereco", event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Número"}
                value={data.numero}
                placeholder={"Digite seu número"}
                onChange={(event) => dataChanger("numero", event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
        </>
    )
}