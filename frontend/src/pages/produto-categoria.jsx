import { useEffect, useRef, useState } from "react";
import { api } from "../shared/api";
import { Trash, Pencil } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";
import { Select } from "../components/select";

export const ProdutoCategoriaPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [ProdutoCategorias, setProdutoCategorias] = useState([]);
  const [Id, setId] = useState(0);
  const [Categoria, setCategoria] = useState(0);
  const [Produto, setProduto] = useState(0);
  const [IdProduto, setIdProduto] = useState(0);
  const [Categorias, setCategorias] = useState([]);
  const [Produtos, setProdutos] = useState([]);

  const salvar = (event) => {
    event.preventDefault();

    const data = {
      id_categorias: Number(Categoria),
      id_produtos: Number(Produto),
    };

    if (Id) {
      api.put(`/produtos-categorias/${Id}`, data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Produto categoria alterado com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/produtos-categorias", data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Produto categoria cadastrado com sucesso!",
          icon: "success",
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/produtos-categorias/${id}`).then((response) => {
      setShowModal(true);
      setCategoria(response.data.id_categorias);
      setProduto(response.data.id_produtos);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este produto categoria?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/produtos-categorias/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Produto categoria deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setCategoria(0);
    setProduto(0);
  };

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  useEffect(() => {
    api.get("/categorias").then((response) => {
      setCategorias(response.data);
    });
    api.get("/produtos").then((response) => {
      setProdutos(response.data);
    });
  }, []);

  useEffect(() => {
    if (IdProduto)
      api
        .get(`/produtos-categorias/categorias-por-produto/${IdProduto}`)
        .then((response) => {
          setProdutoCategorias(response.data);
        });
  }, [IdProduto, Reload]);

  return (
    <div className="flex max-h-screen w-full flex-col p-2 sm:p-8">
      <div className="mb-[33px] flex w-full flex-wrap justify-center gap-2 border-b border-[#d9d9d9] pb-[12px] sm:justify-between">
        <h1 className="text-center text-[38px] font-semibold leading-[140%]">
          Produto Categorias
        </h1>
        <Select
          onChange={(e) => setIdProduto(Number(e.target.value))}
          value={IdProduto}
          FitContent
        >
          <option>Selecione o produto</option>
          {Produtos.map((m) => (
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
              <th>Categoria</th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {ProdutoCategorias?.map((m) => (
              <tr key={m?.id}>
                <td>{m?.categoria?.nome}</td>
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
            {Id ? "Editar" : "Cadastrar"} Produto Categoria
          </h1>
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
          <Select
            Label={"Categoria"}
            onChange={(e) => setCategoria(e.target.value)}
            value={Categoria}
          >
            <option>Selecione a categoria</option>
            {Categorias.map((m) => (
              <option key={m.id} value={m.id}>
                {m.nome}
              </option>
            ))}
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
