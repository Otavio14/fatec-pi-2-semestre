import { useEffect, useState } from "react";
import { api } from "../../shared/api"
import { getAuthId } from "../../shared/auth";
import { useNavigate } from "react-router-dom";

export const ProfilePedidos = ({ show, setShow, pedidos }) => {

    const Navigate = useNavigate();

    return (
        <div className={`${!!show ? "shadow-md bg-gray-100 p-6 rounded-lg" : "hidden"}`}>
            <h2 className="text-2xl font-bold mb-4">Meus Pedidos</h2>
            {pedidos.length >= 1 ? pedidos.map((pedido) => (
                <div
                    className="bg-white p-4 rounded-md shadow-sm border mb-4 hover:cursor-pointer hover:scale-105 hover:transition-all"
                    onClick={() => {
                        Navigate('/meu-pedido', { state: { idPedido: pedido.id } })
                    }}
                >
                    <p>Código: {pedido.id}</p>
                    <p>Status: {pedido.status}</p>
                    <p>{pedido.endereco}</p>
                    <p>Realizado em {(() => {
                        const [year, month, day] = pedido.dtPedido.split("T")[0].split("-");
                        return `${day}/${month}/${year}`;
                    })()}</p>
                </div>

            )) : (<p>Você não fez nenhum pedido</p>)}
        </div>
    )
}