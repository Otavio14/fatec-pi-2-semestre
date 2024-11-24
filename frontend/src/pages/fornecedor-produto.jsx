import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { api } from "../shared/api";
import { Trash, Pencil } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";
import { Select } from "../components/select";

export const FornecedorProdutoPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [FornecedorProdutos, setFornecedorProdutos] = useState([]);
  const [Id, setId] = useState(0);
  const [Preco, setPreco] = useState(0);
  const [Quantidade, setQuantidade] = useState(0);
  const [Fornecedor, setFornecedor] = useState(0);
  const [Produto, setProduto] = useState(0);
  const [IdFornecedor, setIdFornecedor] = useState(0);
  const [Fornecedores, setFornecedores] = useState([]);
  const [Produtos, setProdutos] = useState([]);
  const [Data, setData] = useState("");

  const salvar = (event) => {
    event.preventDefault();

    const data = {
      preco: Number(Preco),
      idFornecedor: Number(Fornecedor),
      idProduto: Number(Produto),
      quantidade: Number(Quantidade),
      data: Data,
    };

    if (Id) {
      api.put(`/fornecedores_produtos/${Id}`, data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Fornecedor produto alterada com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/fornecedores_produtos", data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Fornecedor produto cadastrado com sucesso!",
          icon: "success",
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/fornecedores_produtos/${id}`).then((response) => {
      setShowModal(true);
      setPreco(response.data.preco);
      setQuantidade(response.data.quantidade);
      setFornecedor(response.data.idFornecedor);
      setProduto(response.data.idProduto);
      setData(response.data.data);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este fornecedor produto?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/fornecedores_produtos/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Fornecedor produto deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setPreco(0);
    setQuantidade(0);
    setFornecedor(0);
    setProduto(0);
    setData("");
  };

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  useEffect(() => {
    api.get("/fornecedores").then((response) => {
      setFornecedores(response.data);
    });
    api.get("/produtos").then((response) => {
      setProdutos(response.data);
    });
  }, []);

  useEffect(() => {
    if (IdFornecedor)
      api
        .get(`/fornecedores_produtos/fornecedor/${IdFornecedor}`)
        .then((response) => {
          setFornecedorProdutos(response.data);
        });
  }, [IdFornecedor, Reload]);

  return (
    <div className="flex max-h-screen w-full flex-col p-2 sm:p-8">
      <div className="mb-[33px] flex w-full flex-wrap justify-center gap-2 border-b border-[#d9d9d9] pb-[12px] text-center sm:justify-between">
        <h1 className="text-[38px] font-semibold leading-[140%]">
          Fornecedor Produtos
        </h1>
        <Select
          onChange={(e) => setIdFornecedor(Number(e.target.value))}
          value={IdFornecedor}
          FitContent
        >
          <option>Selecione o fornecedor</option>
          {Fornecedores.map((m) => (
            <option key={m.id} value={m.id}>
              {m.nome}
            </option>
          ))}
        </Select>
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
              <th>Produto</th>
              <th>Preço</th>
              <th>Quantidade</th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {FornecedorProdutos?.map((m) => (
              <tr key={m?.id}>
                <td>{m?.produto}</td>
                <td>{m?.preco}</td>
                <td>{m?.quantidade}</td>
                <td>
                  <button onClick={() => openModal(m?.id)}>
                    <Pencil size={20} />
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => {
                      deletar(m?.id);
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
            {Id ? "Editar" : "Cadastrar"} Fornecedor Produto
          </h1>
          <Select
            Label={"Fornecedor"}
            onChange={(e) => setFornecedor(e.target.value)}
            value={Fornecedor}
          >
            <option>Selecione o fornecedor</option>
            {Fornecedores.map((m) => (
              <option key={m.id} value={m.id}>
                {m.nome}
              </option>
            ))}
          </Select>
          <Select
            Label={"Produto"}
            onChange={(e) => setProduto(e.target.value)}
            value={Produto}
          >
            <option>Selecione o produto</option>
            {Produtos.map((m) => (
              <option key={m.id} value={m.id}>
                {m.nome}
              </option>
            ))}
          </Select>
          <Input
            type="number"
            placeholder="R$ 000,00"
            Label={"Preço"}
            onChange={(e) => setPreco(Number(e.target.value))}
            value={Preco}
            required
          />
          <Input
            type="number"
            placeholder="00001"
            Label={"Quantidade"}
            onChange={(e) => setQuantidade(Number(e.target.value))}
            value={Quantidade}
            required
          />
          <Input
            type="datetime-local"
            Label={"Data"}
            onChange={(e) => setData(e.target.value)}
            value={Data}
            required
          />
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
