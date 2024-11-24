import { useEffect, useState } from "react";
import { api } from "../../shared/api"
import { getAuthId } from "../../shared/auth";

export const ProfilePedidos = ({ show, setShow, pedidos }) => {

    return (
        <div className={`${!!show ? "shadow-md bg-gray-100 p-6 rounded-lg" : "hidden"}`}>
            <h2 className="text-2xl font-bold mb-4">Meus Pedidos</h2>
            {pedidos.length >= 1 ? pedidos.map((pedido) => (
                <div className="bg-white p-4 rounded-md shadow-sm border mb-4">
                    <p>Código: {pedido.id}</p>
                    <p>Status: {pedido.status}</p>
                    <p>{pedido.endereco}</p>
                    <p>Realizado em {pedido.dtPedido.split("T")[0]}</p>
                </div>

            )) : (<p>Você não fez nenhum pedido</p>)}
        </div>
    )
}