import { useEffect, useState } from "react";
import { Card } from "../components/card.jsx";
import { api } from "../shared/api.js";

export const HomePage = () => {
  const [Produtos, setProdutos] = useState([]);

  useEffect(() => {
    api.get("/produtos").then((res) => {
      setProdutos(res.data);
    });
  }, []);

  return (
    <div className="flex flex-col items-center p-0 sm:p-20">
      <h1 className="mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] pl-4 text-[38px] font-semibold leading-[140%]">
        Produtos
      </h1>
      <div className="grid grid-cols-1 gap-[20px] md:grid-cols-2 xl:grid-cols-3">
        {Produtos.map((produto, i) => (
          <Card
            key={i}
            Text={produto.nome}
            Price={produto.preco}
            Image={produto.imagem}
            Id={produto.id}
            Nota={produto?.nota}
            Estoque={produto?.estoque}
          />
        ))}
      </div>
    </div>
  );
};
