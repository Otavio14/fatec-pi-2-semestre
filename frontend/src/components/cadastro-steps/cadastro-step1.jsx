import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { Input } from "../input";

export const Step1 = ({ data, updateFieldData }) => {

    return (
        <>
            <Input
                Label={"Nome Completo"}
                value={data.nome || ""}
                placeholder={"Digite seu nome e sobrenome"}
                onChange={(e) => updateFieldData("nome", e.target.value)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Email"}
                value={data.email || ""}
                placeholder={"Digite seu email"}
                onChange={(e) => updateFieldData("email", e.target.value)}
                required
                type="email"
                autoComplete="email"
            />
            <Input
                Label={"Telefone"}
                value={data.telefone || ""}
                placeholder={"Digite seu telefone"}
                onChange={(e) => updateFieldData("telefone", e.target.value.replace(/\D/g, "")
                    .replace(/(\d{2})(\d)/, "($1) $2")
                    .replace(/(\d{5})(\d)/, "$1-$2")
                    .slice(0, 14),)}
                required
                type="text"
                autoComplete="text"
            />
            <Input
                Label={"Senha"}
                value={data.senha || ""}
                placeholder={"*********"}
                onChange={(e) => updateFieldData("senha", e.target.value)}
                required
                type="password"
                autoComplete="password"
            />
        </>
    )
}