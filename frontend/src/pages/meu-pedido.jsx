import { NavLink, useLocation } from "react-router-dom";
import { api } from "../shared/api";
import { useEffect, useState } from "react";

export const MeuPedido = () => {
    const location = useLocation();
    const { idPedido, dtPedido, statusPedido } = location.state || {};

    const [produtos, setProdutos] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (idPedido) {
            api
                .get(`produtos-pedidos/pedido/${idPedido}`)
                .then((res) => {
                    setProdutos(res.data);
                    console.log({ Bigas: produtos })
                    setIsLoading(false);
                })
                .catch((err) => {
                    console.error(err);
                    setError("Erro ao buscar os produtos do pedido.");
                    setIsLoading(false);
                });
        } else {
            setError("ID do pedido não fornecido.");
            setIsLoading(false);
        }
    }, [idPedido]);

    if (isLoading) {
        return (
            <div className="flex justify-center items-center min-h-screen bg-gray-50">
                <p className="text-lg font-medium text-gray-700">Carregando...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="flex justify-center items-center min-h-screen bg-gray-50">
                <p className="text-lg font-medium text-red-500">{error}</p>
            </div>
        );
    }

    return (
        <div className="flex flex-col items-center px-4 py-10 sm:px-20">
            <div className="mb-[33px] flex w-full items-center justify-between border-b border-[#d9d9d9] pb-[12px]">
                <h1 className="text-[38px] font-semibold leading-[140%]">Detalhes do Pedido</h1>
                <p className="text-[18px]"><span className="font-semibold">Realizado: </span>{(() => {
                    const [year, month, day] = dtPedido.split("T")[0].split("-");
                    return `${day}/${month}/${year}`;
                })()}</p>
                <p className="text-[18px]"><span className="font-semibold">Status: </span>{statusPedido}</p>
                <NavLink
                    to="/perfil"
                    className="flex items-center justify-center rounded-full bg-white p-2 shadow-lg transition-all duration-300 hover:bg-[#dd3842] hover:text-white hover:-translate-x-1"
                >
                    <span className="font-medium">Voltar</span>
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="currentColor"
                        viewBox="0 0 24 24"
                        className="h-6 w-6"
                    >
                        <path d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z" />
                    </svg>
                </NavLink>
            </div>
            <div className="w-full bg-white">
                {produtos.map((produto, index) => (
                    <div
                        key={index}
                        className="grid w-full grid-cols-[auto,1fr,66px,100px] items-center gap-4 border-b border-[#d9d9d9]"
                    >
                        <div className="flex h-[75px] md:h-[100px] w-[50px] md:w-[100px] items-center justify-center rounded border p-[10px]">
                            <img
                                src={produto.produto.imagem || "https://imgs.search.brave.com/cGHXECvvjKMbdnOSP6cjgNCny8YO5tnC0JjxnHJInZQ/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly90bXNz/bC5ha2FtYWl6ZWQu/bmV0Ly9pbWFnZXMv/d2FwcGVuL2hlYWRl/clJ1bmQvMTAyMy5w/bmc_bG09MTQxMTIw/NDk4Mw"} // Insira um placeholder padrão caso a imagem não exista
                                alt={produto.produto.nome}
                                className="h-full w-full max-w-[70px] object-contain"
                            />
                        </div>
                        <h2 className="text-[18px] font-semibold leading-[140%]">
                            {produto.produto.nome}
                        </h2>
                        <input
                            readOnly
                            value={produto.quantidade}
                            type="number"
                            className="h-[28px] w-[66px] rounded border border-[#8f9eb2] px-[10px]"
                        />
                        <p className="text-[18px] font-semibold leading-[140%]">
                            {produto.preco.toLocaleString("pt-br", {
                                style: "currency",
                                currency: "BRL",
                            })}
                        </p>
                    </div>
                ))}
            </div>
            <h2 className="mt-[20px] self-end text-[24px] font-semibold leading-[140%]">
                Total:{" "}
                {produtos
                    .reduce((total, produto) => total + produto.preco * produto.quantidade, 0)
                    .toLocaleString("pt-br", { style: "currency", currency: "BRL" })}
            </h2>
        </div>
    );
};
