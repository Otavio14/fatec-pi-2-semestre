import { NavLink, useNavigate } from "react-router-dom";
import UserSvg from "../assets/user.svg";
import CarrinhoSvg from "../assets/carrinho.svg";
import { useEffect, useState } from "react";
import { X } from "@phosphor-icons/react";
import Icone from "../assets/icone-2.png";
import { Swal } from "../shared/swal";

export const HeaderComponent = () => {
  const Navigate = useNavigate();
  const [CarrinhoQuantidade, setCarrinhoQuantidade] = useState(0);
  const [CarrinhoTotal, setCarrinhoTotal] = useState(0);
  const [CarrinhoProdutos, setCarrinhoProdutos] = useState([]);
  const [CarrinhoOpen, setCarrinhoOpen] = useState(false);

  const refreshCarrinho = () => {
    const local = JSON.parse(localStorage.getItem("carrinho") || "{}");

    if (!local || !local.produtos || !local.total || !local.quantidade) {
      localStorage.setItem(
        "carrinho",
        JSON.stringify({ produtos: [], total: 0, quantidade: 0 }),
      );
    } else {
      const { produtos, total, quantidade } = local;
      setCarrinhoProdutos(produtos);
      setCarrinhoTotal(total);
      setCarrinhoQuantidade(quantidade);
    }
  };

  const updateCarrinho = (produto, quantidade) => {
    if (quantidade < 1) {
      const produtos = CarrinhoProdutos.filter((f) => f.id !== produto.id);
      const total = CarrinhoTotal - produto.preco;
      const qtde = CarrinhoQuantidade - 1;
      setCarrinhoProdutos(produtos);
      setCarrinhoTotal(total);
      setCarrinhoQuantidade(qtde);

      localStorage.setItem(
        "carrinho",
        JSON.stringify({
          produtos: produtos,
          total: total,
          quantidade: qtde,
        }),
      );
    } else {
      const produtos = CarrinhoProdutos.map((f) =>
        f.id === produto.id ? { ...f, quantidade: parseInt(quantidade) } : f,
      );
      const total =
        CarrinhoTotal + produto.preco * (quantidade - produto.quantidade);
      const qtde = produtos.reduce((acc, curr) => acc + curr.quantidade, 0);
      setCarrinhoProdutos(produtos);
      setCarrinhoTotal(total);
      setCarrinhoQuantidade(qtde);

      localStorage.setItem(
        "carrinho",
        JSON.stringify({
          produtos: produtos,
          total: total,
          quantidade: qtde,
        }),
      );
    }
  };

  const finalizarCompra = () => {
    Navigate("/finalizar-compra");
    setCarrinhoOpen(false);
  };

  useEffect(() => {
    refreshCarrinho();

    window.addEventListener("storage", (event) => {
      if (event.key === "carrinho") {
        refreshCarrinho();
        setCarrinhoOpen(true);
      }
    });
  }, []);

  return (
    <div className="flex flex-col">
      <div className="flex h-[123px] w-full max-w-full items-center justify-between border-b border-[#b4becb] bg-white px-4">
        <div className="h-full py-4">
          <img className="object-fit h-full" src={Icone} />
        </div>
        <div className="hidden items-center justify-center gap-4 sm:flex">
          <NavLink
            end
            to="/"
            className="text-[16px] font-[500] leading-[26px] transition-colors hover:text-[#dd3842] active:text-[#dd3842]"
          >
            Home
          </NavLink>
          <NavLink
            to="/contato"
            className="text-[16px] font-[500] leading-[26px] transition-colors hover:text-[#dd3842] active:text-[#dd3842]"
          >
            Contato
          </NavLink>
        </div>
        <div className="flex h-full w-fit gap-4 sm:gap-8">
          <NavLink to="/login" className="flex items-center gap-[12px]">
            <img src={UserSvg} className="h-6 w-6 object-cover" />
            <div className="hidden flex-col sm:flex">
              <p className="text-[12px] font-semibold leading-[15px] text-[#8f9eb2]">
                Login
              </p>
              <p className="text-[16px] leading-[26px] text-[#0c2d57]">Conta</p>
            </div>
          </NavLink>
          <div
            className="flex cursor-pointer items-center gap-5"
            onClick={() => setCarrinhoOpen(true)}
          >
            <div className="relative w-fit">
              <img src={CarrinhoSvg} />
              <span className="absolute bottom-auto left-auto right-[-11px] top-[-4px] flex h-5 w-5 items-center justify-center rounded-[9px] bg-[#e87a80] text-[12px] leading-[17px] text-white">
                {CarrinhoQuantidade > 9 ? "9+" : CarrinhoQuantidade}
              </span>
            </div>
            <div>
              <p className="text-[12px] font-semibold leading-[15px] text-[#8f9eb2]">
                Carrinho
              </p>
              <p className="text-[16px] font-bold text-[#0c2d57]">
                R$ {CarrinhoTotal?.toFixed(2)}
              </p>
            </div>
          </div>
        </div>
      </div>
      <div className="flex h-[70px] w-full items-center justify-center gap-4 bg-white sm:hidden">
        <NavLink
          to="/"
          className="text-[16px] font-[500] leading-[26px] transition-colors hover:text-[#dd3842] active:text-[#dd3842]"
        >
          Home
        </NavLink>
        <NavLink
          to="/contato"
          className="text-[16px] font-[500] leading-[26px] transition-colors hover:text-[#dd3842] active:text-[#dd3842]"
        >
          Contato
        </NavLink>
      </div>
      {/* Carrinho */}
      <div
        className={`fixed left-0 top-0 z-[15] w-screen grid-cols-[1fr,auto] overflow-y-hidden bg-black/80 ${CarrinhoOpen ? "grid" : "hidden"}`}
      >
        <span className="w-full" onClick={() => setCarrinhoOpen(false)}></span>
        <div className="grid h-screen w-full min-w-[320px] max-w-[480px] grid-cols-1 grid-rows-[auto,1fr,auto] overflow-auto bg-white px-[40px] pb-[50px] pt-[40px]">
          <div className="mb-4 flex items-center justify-between border-b border-b-[#e6e6e6]">
            <h4 className="text-[20px] font-semibold leading-[28px]">
              Carrinho
            </h4>
            <button onClick={() => setCarrinhoOpen(false)}>
              <X size={32} />
            </button>
          </div>
          <div className="flex w-full flex-col gap-4 overflow-auto font-medium">
            {CarrinhoProdutos.map((produto, index) => (
              <div
                key={index}
                className="grid grid-cols-[auto,1fr,auto] items-center gap-2"
              >
                <div className="flex h-[100px] w-[100px] items-center justify-center rounded border p-[10px]">
                  <img
                    src={produto.imagem}
                    className="h-full w-full max-w-[70px] object-contain"
                  />
                </div>
                <div>
                  <p>{produto.nome}</p>
                  <p className="font-bold text-[#dd3842]">
                    R$ {produto.preco?.toFixed(2)}
                  </p>
                </div>
                <input
                  onChange={(e) => {
                    updateCarrinho(produto, e.target.value);
                  }}
                  value={produto.quantidade}
                  type="number"
                  className="h-[28px] w-[66px] rounded border border-[#8f9eb2] px-[10px]"
                />
              </div>
            ))}
          </div>
          <div>
            <div className="mb-[16px] flex items-center justify-between border-t border-[#b4becb] pt-[5px] font-bold">
              <h5>Subtotal</h5>
              <p>R$ {CarrinhoTotal?.toFixed(2)}</p>
            </div>
            <button
              onClick={() => {
                if (CarrinhoQuantidade > 0) finalizarCompra();
                else
                  Swal.fire({
                    title: "Carrinho Vazio",
                    text: "Adicione produtos ao carrinho para finalizar a compra!",
                    icon: "info",
                  });
              }}
              className="w-full rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
            >
              Finalizar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
