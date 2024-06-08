import { useEffect, useState } from "react";
import { Input } from "../components/input.jsx";
import { Select } from "../components/select.jsx";

export const FinalizarCompraPage = () => {
  const [CarrinhoTotal, setCarrinhoTotal] = useState(0);
  const [CarrinhoProdutos, setCarrinhoProdutos] = useState([]);
  const [Confirmacao, setConfirmacao] = useState(false);

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
      {/* Lista de Produtos */}
      <div
        className={`flex w-full flex-col items-center ${Confirmacao ? "hidden" : "flex"}`}
      >
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
        <h2 className="mt-[20px] self-end text-[24px] font-semibold leading-[140%]">
          Total:{" "}
          {CarrinhoTotal.toLocaleString("pt-br", {
            style: "currency",
            currency: "BRL",
          })}
        </h2>
        <button
          onClick={() => setConfirmacao(true)}
          className="w-fit rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
        >
          Continuar
        </button>
      </div>
      {/* Formulário sobre o cliente */}
      <form className={`w-full flex-col ${Confirmacao ? "flex" : "hidden"}`}>
        <h2 className="mt-[20px] w-full pb-[12px] text-center text-[24px] font-semibold leading-[140%]">
          Informações do Cliente
        </h2>
        <div className="grid grid-cols-3 gap-x-4">
          <Input
            Label="Nome"
            type="text"
            name="nome"
            placeholder="Nome Completo"
            required
          />
          <Input
            Label="E-mail"
            type="email"
            name="email"
            placeholder="E-mail"
            required
          />
          <Input
            Label="Telefone"
            type="tel"
            name="telefone"
            placeholder="(00) 00000-0000"
            required
          />
          <Input
            Label="CEP"
            type="text"
            name="cep"
            placeholder="00000-000"
            required
          />
          <Select Label={"Estado"}>
            <option>Selecione o estado</option>
          </Select>
          <Select Label={"Cidade"}>
            <option>Selecione a cidade</option>
          </Select>
          <Input
            Label="Endereço"
            type="text"
            name="endereco"
            placeholder="Endereço"
            required
          />
          <Input
            Label="Número"
            type="number"
            name="numero"
            placeholder="Número"
            required
          />
          <Input
            Label="Bairro"
            type="text"
            name="bairro"
            placeholder="Bairro"
            required
          />
        </div>
        <div className="mt-8 flex items-center justify-center gap-20">
          <button
            onClick={() => setConfirmacao(false)}
            type="button"
            className="w-fit rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
          >
            Voltar
          </button>
          <button
            className="w-fit rounded border bg-[#2B38D1] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
            type="submit"
          >
            Finalizar Compra
          </button>
        </div>
      </form>
    </div>
  );
};
