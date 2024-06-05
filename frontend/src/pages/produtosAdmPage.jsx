import { useEffect, useState } from "react";
import { Card } from "../components/card.jsx";
import { NavLink } from "react-router-dom";
import { api } from "../shared/api.js";

export const ProdutosPage = () => {
    const [Produtos, setProdutos] = useState([]);

    useEffect(() => {
        api.get("/produtos").then((res) => {
            setProdutos(res.data)
        })
    }, []);

    return (
        <div className="flex flex-col items-center p-20">
            <div className="flex flex-row justify-between mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] text-[38px] font-semibold leading-[140%]">
                Produtos
                <button
                    className="flex border rounded-lg px-2 bg-[#0c2d57] text-[#f8f9ff] hover:bg-[#f8f9ff] hover:text-[#0c2d57] active:bg-[white] focus:outline-none focus:ring focus:ring-blue-300 ...">
                    Adicionar
                </button>
            </div>
            <div className="grid grid-cols-1 gap-[20px] md:grid-cols-2 xl:grid-cols-3">
                {Produtos.map((produto, i) => (
                    <NavLink key={i} to={`/produto/${produto?.id}`}>
                        <Card
                            Text={produto.nome}
                            Price={produto.preco}
                            Image={produto.imagem}
                        // Nota={produto.Nota} Para evitar confusão por enquanto
                        />
                    </NavLink>
                ))}
            </div>
        </div>
    );
};
