import { NavLink } from "react-router-dom";
import UserSvg from "../assets/user.svg";
import CarrinhoSvg from "../assets/carrinho.svg";
import { useEffect, useState } from "react";
import { X } from "@phosphor-icons/react";

export const HeaderComponent = () => {
  const [CarrinhoQuantidade, setCarrinhoQuantidade] = useState(0);
  const [CarrinhoTotal, setCarrinhoTotal] = useState(0);
  const [CarrinhoProdutos, setCarrinhoProdutos] = useState([
    {
      id: 1,
      nome: "Whey Protein",
      preco: 83.99,
      quantidade: 5,
      imagem:
        "https://static.netshoes.com.br/produtos/nutri-whey-protein-900-g-pote-integralmedica/99/252-0951-799/252-0951-799_zoom1.jpg?ts=1695093963&ims=544x",
    },
    {
      id: 2,
      nome: "Whey Protein Gold Standard",
      preco: 279.9,
      quantidade: 4,
      imagem:
        "https://m.media-amazon.com/images/I/61GDn0-MvwL._AC_UF1000,1000_QL80_.jpg",
    },
  ]);
  const [CarrinhoOpen, setCarrinhoOpen] = useState(true);

  useEffect(() => {
    const local = JSON.parse(localStorage.getItem("carrinho") || "{}");

    if (!local || !local.produtos || !local.total || !local.quantidade) {
      localStorage.setItem(
        "carrinho",
        JSON.stringify({ produtos: [], total: 0, quantidade: 0 }),
      );
    } else {
      const { produtos, total, quantidade } = JSON.parse(local);
      setCarrinhoProdutos(produtos);
      setCarrinhoTotal(total);
      setCarrinhoQuantidade(quantidade);
    }
  }, []);

  return (
    <div className="flex flex-col">
      <div className="flex h-[123px] w-full max-w-full items-center justify-between border-b border-[#b4becb] bg-white px-4">
        <NavLink to="/" className="flex items-center gap-4 justify-self-start">
          <img
            alt="Logo da empresa"
            src="https://cdn-icons-png.flaticon.com/512/2964/2964514.png"
            className="m-0 h-12 w-12 object-contain"
          />
          <p>Suplementos</p>
        </NavLink>
        <div className="hidden items-center justify-center gap-4 sm:flex">
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
      <div
        className={`fixed left-0 top-0 z-[15] w-screen grid-cols-[1fr,auto] bg-black/80 ${CarrinhoOpen ? "grid" : "hidden"}`}
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
          <div className="flex w-full flex-col gap-4 font-medium">
            {CarrinhoProdutos.map((produto) => (
              <div
                key={produto.id}
                className="grid grid-cols-[auto,1fr,auto] items-center gap-2"
              >
                <div className="h-[100px] w-[100px] rounded border p-[10px]">
                  <img
                    src={produto.imagem}
                    className="object-fit h-full w-full max-w-[70px]"
                  />
                </div>
                <div>
                  <p>{produto.nome}</p>
                  <p className="font-bold text-[#dd3842]">
                    R$ {produto.preco?.toFixed(2)}
                  </p>
                </div>
                <input
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
            <button className="w-full rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]">
              Finalizar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
