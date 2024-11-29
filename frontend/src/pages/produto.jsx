import { Fragment, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Star from "../assets/star.png";
import { api } from "../shared/api";
import { getAuthId, isAuthenticated } from "../shared/auth";
import { Swal, Toast } from "../shared/swal";

export const ProdutoPage = () => {
  const { id } = useParams();
  const ProdutoId = Number(id);

  const [Produto, setProduto] = useState({});
  const [Quantidade, setQuantidade] = useState(1);
  const [Avaliacoes, setAvaliacoes] = useState([]);
  const [Comentario, setComentario] = useState("");
  const [Nota, setNota] = useState(0);
  const [Reload, setReload] = useState(false);
  const idCliente = getAuthId();

  const adicionarAoCarrinho = () => {
    const carrinho = JSON.parse(localStorage.getItem("carrinho") || "{}");
    const newValue = JSON.stringify({
      ...carrinho,
      produtos: carrinho.produtos.find((produto) => produto.id === ProdutoId)
        ? carrinho.produtos.map((produto) =>
            produto.id === ProdutoId
              ? {
                  ...produto,
                  quantidade:
                    produto.quantidade + Quantidade > Produto.estoque
                      ? Produto.estoque
                      : produto.quantidade + Quantidade,
                }
              : produto,
          )
        : [
            ...carrinho.produtos,
            {
              id: ProdutoId,
              nome: Produto.nome,
              preco: Produto.preco,
              quantidade:
                Quantidade > Produto.estoque ? Produto.estoque : Quantidade,
              imagem: Produto.imagem,
              estoque: Produto.estoque,
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
    api.get(`/avaliacoes/produto/${Number(ProdutoId)}`).then((response) => {
      setAvaliacoes(response.data);
      const found = response.data?.find(
        (f) => Number(f?.idCliente) === Number(idCliente),
      );
      if (found) {
        setComentario(found?.comentario);
        setNota(found?.nota);
      }
    });
  }, [ProdutoId, idCliente, Reload]);

  const avaliar = (event) => {
    event.preventDefault();

    const data = {
      comentario: Comentario,
      nota: Nota,
      idProduto: ProdutoId,
      idCliente: idCliente,
    };
    const found = Avaliacoes?.find(
      (f) => Number(f?.idCliente) === Number(idCliente),
    );

    if (found && found?.id) {
      api.put(`/avaliacoes/${found?.id}`, data).then(() => {
        setReload((r) => !r);
        Toast.fire({
          title: "Avaliação alterada com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/avaliacoes", data).then(() => {
        setReload((r) => !r);
        Toast.fire({
          title: "Avaliação cadastrada com sucesso!",
          icon: "success",
        });
      });
    }
  };

  return (
    <div className="flex flex-col items-center bg-[#f8f9ff] py-20 sm:p-20">
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
              onChange={(e) => {
                const q = Number(e.target.value);

                if (q < 1) setQuantidade(1);
                else if (q >= 100) setQuantidade(99);
                else if (q > Produto.estoque) {
                  setQuantidade(Produto.estoque);
                  Swal.fire({
                    icon: "warning",
                    title: "Atenção!",
                    text: "Você alcançou o limite de estoque deste produto!",
                  });
                } else setQuantidade(Number(e.target.value));
              }}
              min={1}
              max={99}
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
      <h2 className="mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] pl-4 text-[38px] font-semibold leading-[140%]">
        Avaliações
      </h2>
      {Avaliacoes?.filter((f) => Number(f?.idCliente) !== Number(idCliente))
        ?.length === 0 && !idCliente ? (
        <h3 className="m-10 w-full pb-[12px] pl-4 text-center text-[22px] font-semibold leading-[140%]">
          Esste produto ainda não recebeu avaliações!
        </h3>
      ) : null}
      <div className="flex w-full max-w-3xl flex-col gap-8">
        {isAuthenticated() && (
          <form className="grid items-center gap-4" onSubmit={avaliar}>
            <h2 className="text-2xl font-semibold">Avalie o Produto</h2>
            <textarea
              value={Comentario}
              rows={3}
              onChange={(e) => setComentario(e.target.value)}
              placeholder="Escreva aqui o que achou do nosso produto..."
              className="resize-none p-4"
            ></textarea>
            <div className="flex justify-between">
              <div className="flex gap-4">
                {Array(5)
                  ?.fill(null)
                  ?.map((_, index) => (
                    <img
                      key={index}
                      src={Star}
                      onClick={() => setNota(index + 1)}
                      className={`h-[24px] w-[24px] cursor-pointer opacity-${index + 1 <= Nota ? "100" : "30"} hover:opacity-100`}
                    />
                  ))}
              </div>
              <button
                style={{ transition: "all 0.3s ease-in-out" }}
                className="text-md w-fit rounded-lg bg-[#dd3842] px-12 py-2 font-semibold text-white shadow-md hover:bg-[#c32f39] hover:shadow-lg"
              >
                Avaliar
              </button>
            </div>
            <hr />
          </form>
        )}
        <div className="grid h-full grid-cols-[auto,1fr] gap-y-8">
          {Avaliacoes?.filter(
            (f) => Number(f?.idCliente) !== Number(idCliente),
          )?.map((avaliacao) => (
            <Fragment key={avaliacao.id}>
              {/* flex h-full w-full max-w-[500px] items-center justify-center rounded border border-[#e7eaee] bg-white py-4 sm:h-[500px] sm:py-0 */}
              <div className="flex flex-col items-center justify-center rounded-l border-b border-l border-t border-[#e7eaee] bg-white p-6">
                <span>{avaliacao?.cliente?.nome}</span>
                <span className="text-xs">
                  {new Date(avaliacao?.dtAvaliacao).toLocaleDateString("pt-BR")}
                </span>
              </div>
              <div className="flex flex-col gap-4 rounded-r border-b border-r border-t border-[#e7eaee] bg-white p-6">
                <div className="mb-1 flex items-center gap-1">
                  {Array(avaliacao.nota || 0)
                    ?.fill(null)
                    ?.map((_, index) => (
                      <img
                        key={index}
                        src={Star}
                        className="h-[18px] w-[18px]"
                      />
                    ))}
                </div>
                <div>{avaliacao?.comentario}</div>
              </div>
            </Fragment>
          ))}
        </div>
      </div>
    </div>
  );
};
