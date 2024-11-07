import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { Input } from "../input";

export const Step1 = ({ data, dataChanger }) => {

    return (
        <>
            <Input
                Label={"Nome Completo"}
                value={data.nome}
                placeholder={"Digite seu nome e sobrenome"}
                onChange={(event) => dataChanger("nome", event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Email"}
                value={data.email}
                placeholder={"Digite seu email"}
                onChange={(event) => dataChanger("email", event.target.value)}
                required
                type="email"
                autoComplete="email"
            />
            <Input
                Label={"Senha"}
                value={data.senha}
                placeholder={"*********"}
                onChange={(event) => dataChanger("senha", event.target.value)}
                required
                type="password"
                autoComplete="password"
            />
        </>
    )
}