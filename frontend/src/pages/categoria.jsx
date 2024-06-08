import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { api } from "../shared/api";
import { Trash, Pencil } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";

export const CategoriaPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [Categorias, setCategorias] = useState([]);
  const [Id, setId] = useState(0);
  const [Nome, setNome] = useState("");

  const salvar = (event) => {
    event.preventDefault();

    const data = {
      nome: Nome,
    };

    if (Id) {
      api.put(`/categorias/${Id}`, data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Categoria alterada com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/categorias", data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Categoria cadastrado com sucesso!",
          icon: "success",
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/categorias/${id}`).then((response) => {
      setShowModal(true);
      setNome(response.data.nome);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este categoria?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/categorias/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Categoria deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setNome("");
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
  }, [Reload]);

  return (
    <div className="flex w-full flex-col p-8">
      <div className="mb-[33px] flex w-full justify-between border-b border-[#d9d9d9] pb-[12px]">
        <h1 className="text-[38px] font-semibold leading-[140%]">Categorias</h1>
        <button
          className="w-fit rounded bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white"
          onClick={() => setShowModal(true)}
        >
          Cadastrar
        </button>
      </div>
      <table className="border-collapse border bg-white">
        <thead>
          <tr>
            <th>Nome</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {Categorias?.map((categoria) => (
            <tr key={categoria?.id}>
              <td>{categoria?.nome}</td>
              <td>
                <button onClick={() => openModal(categoria?.id)}>
                  <Pencil size={20} />
                </button>
              </td>
              <td>
                <button
                  onClick={() => {
                    deletar(categoria?.id);
                  }}
                >
                  <Trash size={20} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <dialog
        ref={DialogRef}
        onCancel={closeModal}
        className={`fixed left-0 top-0 z-[13] h-screen w-screen flex-col items-center overflow-y-auto border-none bg-transparent p-4 backdrop:bg-black/50 ${
          ShowModal ? "flex" : ""
        }`}
      >
        <form
          onSubmit={salvar}
          className="w-fir z-[15] mx-0 my-auto flex h-fit flex-col items-center rounded-lg bg-[#f8f9ff] p-12"
        >
          <h1 className="text-[38px] font-semibold leading-[140%]">
            {Id ? "Editar" : "Cadastrar"} Categoria
          </h1>
          <Input
            type="text"
            placeholder="Nome"
            Label={"Nome"}
            onChange={(e) => setNome(e.target.value)}
            value={Nome}
            required
          />
          <div className="flex gap-4">
            <button
              className="w-fit rounded border bg-[#2B38D1] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
              type="submit"
            >
              Cadastrar
            </button>
            <button
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
