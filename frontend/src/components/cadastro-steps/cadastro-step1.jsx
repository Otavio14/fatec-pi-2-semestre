import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { Input } from "../input";

export const Step1 = ({ show, setShow }) => {

    return (
        <>
            <Input
                Label={"Nome Completo"}
                // value={nome}
                placeholder={"Digite seu nome e sobrenome"}
                // onChange={(event) => setNome(event.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Email"}
                // value={email}
                placeholder={"Digite seu email"}
                // onChange={(event) => setEmail(event.target.value)}
                required
                type="email"
                autoComplete="email"
            />
            <Input
                Label={"Senha"}
                // value={senha}
                placeholder={"*********"}
                // onChange={(event) => setSenha(event.target.value)}
                required
                type="password"
                autoComplete="password"
            />
        </>
    )
}