import { useEffect, useRef, useState } from "react";
import { api } from "../shared/api.js";
import { Input } from "../components/input.jsx";
import Swal from "sweetalert2";
import { Pencil, Trash } from "@phosphor-icons/react";
import { Toast } from "../shared/swal.js";
import { Select } from "../components/select";

export const ProdutosPage = () => {
  const DialogRef = useRef();
  const [Produtos, setProdutos] = useState([]);
  const [NomeProduto, setNomeProduto] = useState("");
  const [PrecoProduto, setPrecoProduto] = useState(Number);
  const [EstoqueProduto, setEstoqueProduto] = useState(Number);
  const [DescricaoProduto, setDescricaoProduto] = useState("");
  const [ValidadeProduto, setValidadeProduto] = useState("");
  const [ImagemProduto, setImagemProduto] = useState("");
  const [Ativo, setAtivo] = useState(0);
  const [Reload, setReload] = useState(false);
  const [ShowModal, setShowModal] = useState(false);
  const [Id, setId] = useState(0);

  const salvar = (event) => {
    event.preventDefault();

    const data = {
      nome: NomeProduto,
      preco: Number(PrecoProduto),
      estoque: Number(EstoqueProduto),
      descricao: DescricaoProduto,
      dtValidade: ValidadeProduto,
      imagem: ImagemProduto,
      ativo: Ativo,
    };

    if (Id) {
      api.put(`/produtos/${Id}`, data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Produto alterado com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/produtos", data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Produto cadastrado com sucesso!",
          icon: "success",
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/produtos/${id}`).then((response) => {
      setShowModal(true);
      setNomeProduto(response.data.nome);
      setPrecoProduto(response.data.preco);
      setEstoqueProduto(response.data.estoque);
      setDescricaoProduto(response.data.descricao);
      setValidadeProduto(response.data.dtValidade);
      setImagemProduto(response.data.imagem);
      setAtivo(response.data.ativo);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este produto?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/produtos/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Produto deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setNomeProduto("");
    setPrecoProduto(0);
    setEstoqueProduto(0);
    setDescricaoProduto("");
    setValidadeProduto("");
    setImagemProduto("");
    setAtivo(0);
  };

  useEffect(() => {
    api.get("/produtos").then((res) => {
      setProdutos(res.data);
    });
  }, [Reload]);

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  return (
    <div className="flex max-h-screen w-full flex-col p-2 sm:p-8">
      <div className="mb-[33px] flex w-full flex-wrap justify-center gap-2 border-b border-[#d9d9d9] pb-[12px] sm:justify-between">
        <h1 className="text-[38px] font-semibold leading-[140%]">Produtos</h1>
        <button
          className="w-fit rounded bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white"
          onClick={() => setShowModal(true)}
        >
          Cadastrar
        </button>
      </div>
      <div className="w-full overflow-auto">
        <table className="w-full border-collapse border bg-white">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Preço</th>
              <th>Estoque</th>
              <th>Validade</th>
              <th>Ativo</th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {Produtos?.map((produto) => (
              <tr key={produto?.id}>
                <td>{produto?.nome}</td>
                <td>{produto?.preco}</td>
                <td>{produto?.estoque}</td>
                <td>
                  {new Date(produto?.dtValidade + "T00:00").toLocaleDateString(
                    "pt-BR",
                  )}
                </td>
                <td>{produto?.ativo ? "Sim" : "Não"}</td>
                <td>
                  <button onClick={() => openModal(produto?.id)}>
                    <Pencil size={20} />
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => {
                      deletar(produto?.id);
                    }}
                  >
                    <Trash size={20} />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <dialog
        ref={DialogRef}
        onCancel={closeModal}
        className={`fixed left-0 top-0 z-[13] h-screen w-screen flex-col items-center overflow-y-auto border-none bg-transparent p-4 backdrop:bg-black/50 ${
          ShowModal ? "flex" : ""
        }`}
      >
        <form
          onSubmit={salvar}
          className="w-fir z-[15] mx-0 my-auto flex h-fit flex-col items-center rounded-lg bg-[#f8f9ff] p-2 sm:p-12"
        >
          <h1 className="text-center text-[38px] font-semibold leading-[140%]">
            Cadastrar Produto
          </h1>
          <Input
            Label={"Nome"}
            placeholder="Nome"
            value={NomeProduto}
            onChange={(e) => setNomeProduto(e.target.value)}
          />
          <Input
            Label={"Preço"}
            type="number"
            placeholder="Preço"
            value={PrecoProduto}
            onChange={(e) => setPrecoProduto(e.target.value)}
          />
          <Input
            Label={"Quantidade em Estoque"}
            type="number"
            placeholder="Qtde em estoque"
            value={EstoqueProduto}
            onChange={(e) => setEstoqueProduto(e.target.value)}
          />
          <Input
            Label={"Data de Validade"}
            type="date"
            value={ValidadeProduto}
            onChange={(e) => setValidadeProduto(e.target.value)}
          />
          <Input
            Label={"URL da Imagem"}
            placeholder="htttp://..."
            value={ImagemProduto}
            onChange={(e) => setImagemProduto(e.target.value)}
          />
          <Input
            Label={"Descrição"}
            Textarea
            Rows={8}
            placeholder="Descrição"
            value={DescricaoProduto}
            onChange={(e) => setDescricaoProduto(e.target.value)}
          />
          <Select
            Label={"Ativo?"}
            onChange={(e) => setAtivo(e.target.value)}
            value={Ativo}
          >
            <option value={0}>Não</option>
            <option value={1}>Sim</option>
          </Select>
          <div className="flex gap-4">
            <button
              className="w-fit rounded border bg-[#2B38D1] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
              type="submit"
            >
              Salvar
            </button>
            <button
              type="button"
              className="w-fit rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
              onClick={closeModal}
            >
              Cancelar
            </button>
          </div>
        </form>
      </dialog>
    </div>
  );
};
