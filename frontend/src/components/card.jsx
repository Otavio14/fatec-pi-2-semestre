import { NavLink } from "react-router-dom";
import Star from "../assets/star.png";

export const Card = ({ Text, Price, Image, Nota, Id }) => {
  const adicionarAoCarrinho = () => {
    const carrinho = JSON.parse(localStorage.getItem("carrinho") || "{}");
    const newValue = JSON.stringify({
      ...carrinho,
      produtos: carrinho.produtos.find((produto) => produto.id === Id)
        ? carrinho.produtos.map((produto) =>
            produto.id === Id
              ? { ...produto, quantidade: produto.quantidade + 1 }
              : produto,
          )
        : [
            ...carrinho.produtos,
            {
              id: Id,
              nome: Text,
              preco: Price,
              quantidade: 1,
              imagem: Image,
            },
          ],
      total: carrinho.total + Price,
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

  return (
    <div
      style={{ transition: "box-shadow .5s" }}
      className="flex h-[440px] w-[315px] flex-col items-center justify-between rounded border border-[#e7eaee] bg-white px-[30px] pb-[28px] pt-[25px] hover:shadow-custom"
    >
      <NavLink to={`/produto/${Id}`}>
        <img
          src={Image}
          alt="Imagem do produto"
          className="h-full max-h-[220px] w-full object-contain"
        />
      </NavLink>
      <div className="flex w-full flex-col items-center gap-[10px] text-center">
        <NavLink to={`/produto/${Id}`}>
          <p className="text-[18px] font-[500] leading-[160%] text-[#5c728e]">
            {Text}
          </p>{" "}
        </NavLink>
        <div className="mb-1 flex items-center gap-1 overflow-hidden">
          {Array(Nota || 0)
            ?.fill(null)
            ?.map((_, index) => (
              <img key={index} src={Star} className="h-[18px] w-[18px]" />
            ))}
        </div>
        <p className="text-[17px] font-[600] leading-[20px] text-[#dd3842]">
          R$ {String(Price?.toFixed(2))?.replace(".", ",")}
        </p>
        <button
          style={{
            fontFamily: "Open Sans, sans-serif",
            transition: "color .2s, background-color .4s",
            transform:
              "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg)",
          }}
          onClick={adicionarAoCarrinho}
          className="h-[44px] w-full items-center justify-center rounded border border-[#0c2d57] bg-transparent py-[10px] text-[#0c2d57] hover:border-[#dd3842] hover:bg-[#dd3842] hover:text-white"
        >
          Adicionar ao Carrinho
        </button>
      </div>
    </div>
  );
};
