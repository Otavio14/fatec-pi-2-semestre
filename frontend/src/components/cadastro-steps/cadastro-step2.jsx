import React from "react";
import { Input } from "../input";

export const Step2 = () => {

    return (
        <>
            <Input
                Label={"CEP"}
                // value={nome}
                placeholder={"Digite seu CEP"}
                // onChange={(event) => setNome(event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Estado"}
                // value={nome}
                placeholder={"Digite seu Estado"}
                // onChange={(event) => setNome(event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Cidade"}
                // value={email}
                placeholder={"Digite sua cidade"}
                // onChange={(event) => setEmail(event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Bairro"}
                // value={email}
                placeholder={"Digite seu bairro"}
                // onChange={(event) => setEmail(event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Rua"}
                // value={senha}
                placeholder={"Digite sua rua"}
                // onChange={(event) => setSenha(event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Número"}
                // value={senha}
                placeholder={"Digite seu número"}
                // onChange={(event) => setSenha(event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
        </>
    )
}