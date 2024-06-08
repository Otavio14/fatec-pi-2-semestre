import { useEffect, useState } from "react";

export const FinalizarCompraPage = () => {
  const [CarrinhoTotal, setCarrinhoTotal] = useState(0);
  const [CarrinhoProdutos, setCarrinhoProdutos] = useState([]);

  useEffect(() => {
    const local = JSON.parse(localStorage.getItem("carrinho") || "{}");

    if (!local || !local.produtos || !local.total || !local.quantidade) return;

    const { produtos, total } = local;
    setCarrinhoProdutos(produtos);
    setCarrinhoTotal(total);
  }, []);

  return (
    <div className="flex flex-col items-center px-20 py-10">
      <h1 className="mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] text-[38px] font-semibold leading-[140%]">
        Finalizar Compra
      </h1>
      <div className="w-full bg-white">
        {CarrinhoProdutos.map((produto, index) => (
          <div
            key={index}
            className="flex w-full items-center justify-between border-b border-[#d9d9d9] py-4"
          >
            <div className="flex items-center">
              <img
                src={produto.imagem}
                alt={produto.nome}
                className="h-[100px] w-[100px] object-cover"
              />
              <div className="ml-[20px]">
                <h2 className="text-[18px] font-semibold leading-[140%]">
                  {produto.nome}
                </h2>
                <p className="text-[14px] leading-[140%] text-[#9b9b9b]">
                  Quantidade: {produto.quantidade}
                </p>
              </div>
            </div>
            <p className="text-[18px] font-semibold leading-[140%]">
              {produto.preco.toLocaleString("pt-br", {
                style: "currency",
                currency: "BRL",
              })}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};
