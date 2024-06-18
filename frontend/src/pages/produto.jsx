import { useEffect, useState } from "react";
import Star from "../assets/star.png";
import { api } from "../shared/api";
import { useParams } from "react-router-dom";

export const ProdutoPage = () => {
  const { id } = useParams();
  const ProdutoId = Number(id);

  const [Produto, setProduto] = useState({});
  const [Quantidade, setQuantidade] = useState(1);
  useEffect(() => {
    console.log(Quantidade)
  }, [Quantidade]);

  const adicionarAoCarrinho = () => {
    const carrinho = JSON.parse(localStorage.getItem("carrinho") || "{}");
    const newValue = JSON.stringify({
      ...carrinho,
      produtos: carrinho.produtos.find((produto) => produto.id === ProdutoId)
        ? carrinho.produtos.map((produto) =>
            produto.id === ProdutoId
              ? { ...produto, quantidade: produto.quantidade + Quantidade }
              : produto,
          )
        : [
            ...carrinho.produtos,
            {
              id: ProdutoId,
              nome: Produto.nome,
              preco: Produto.preco,
              quantidade: Quantidade,
              imagem: Produto.imagem,
            },
          ],
      total: carrinho.total + Quantidade * Produto.preco,
      quantidade: carrinho.quantidade + 1,
    });
    localStorage.setItem("carrinho", newValue);

    const storageEvent = new StorageEvent("storage", {
      key: "carrinho",
      oldValue: carrinho,
      newValue: newValue,
      url: window.location.href,
      storageArea: localStorage,
    });

    window.dispatchEvent(storageEvent);
  };

  useEffect(() => {
    api.get(`/produtos/${Number(ProdutoId)}`).then((response) => {
      setProduto(response.data);
    });
  }, [ProdutoId]);

  return (
    <div className="flex flex-col items-center bg-[#f8f9ff] py-20">
      <div className="flex w-full flex-col items-center justify-center gap-8 p-2 md:flex-row">
        <div className="flex h-full w-full max-w-[500px] items-center justify-center rounded border border-[#e7eaee] bg-white py-4 sm:h-[500px] sm:py-0">
          <img
            className="h-full max-h-[350px] w-full max-w-[350px] object-contain"
            src={Produto.imagem}
          />
        </div>
        <div className="mt-[16px] flex max-w-[581px] flex-col items-stretch">
          <h1 className="mb-[12px] text-[30px] font-semibold leading-[40px]">
            {Produto.nome}
          </h1>
          <div className="mb-1 flex items-center gap-1">
            {Array(Produto.nota || 0)
              ?.fill(null)
              ?.map((_, index) => (
                <img key={index} src={Star} className="h-[18px] w-[18px]" />
              ))}
          </div>
          <p className="mb-[22px] mt-[20px] w-full border-b border-b-[#8f9eb2] pb-[16px] text-[30px] font-semibold leading-[40px] text-[#dd3842]">
            R$ {String(Produto.preco?.toFixed(2))?.replace(".", ",")}
          </p>
          <p className="text-justify leading-[28px] text-[#5c728e]">
            {Produto.descricao}
          </p>
          <div className="mb-[28px] mt-[23px] flex gap-[10px]">
            <input
              type="number"
              value={Quantidade}
              onChange={(e) => setQuantidade(Number(e.target.value))}
              className="h-[50px] w-full max-w-[120px] rounded border border-[#5c728e] bg-transparent pl-[50px] text-[18px] font-medium leading-[28px]"
            />

            <button
              onClick={adicionarAoCarrinho}
              style={{ transition: "color .2s, background-color .4s" }}
              className="w-full rounded border border-[#0c2d57] bg-white px-[15px] text-[#0c2d57] hover:border-[#dd3842] hover:bg-[#dd3842] hover:text-white"
            >
              Adicionar ao Carrinho
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
