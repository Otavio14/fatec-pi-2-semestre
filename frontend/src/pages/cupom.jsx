import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { api } from "../shared/api";
import { Trash, Pencil } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";

export const CupomPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [Cupons, setCupons] = useState([]);
  const [Id, setId] = useState(0);
  const [Nome, setNome] = useState("");
  const [Porcentagem, setPorcentagem] = useState(0);

  const salvar = (event) => {
    event.preventDefault();

    const data = {
      nome: Nome,
      porcentagem: +Porcentagem,
    };

    if (Id) {
      api.put(`/cupons/${Id}`, data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Cupom alterado com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/cupons", data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Cupom cadastrado com sucesso!",
          icon: "success",
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/cupons/${id}`).then((response) => {
      setShowModal(true);
      setNome(response.data.nome);
      setPorcentagem(response.data.porcentagem);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este cupom?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/cupons/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Cupom deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setNome("");
    setPorcentagem(0);
  };

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  useEffect(() => {
    api.get("/cupons").then((response) => {
      setCupons(response.data);
    });
  }, [Reload]);

  return (
    <div className="flex w-full flex-col p-8">
      <div className="mb-[33px] flex w-full justify-between border-b border-[#d9d9d9] pb-[12px]">
        <h1 className="text-[38px] font-semibold leading-[140%]">Cupons</h1>
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
            <th>Porcentagem</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {Cupons?.map((cupom) => (
            <tr key={cupom?.id}>
              <td>{cupom?.nome}</td>
              <td>{cupom?.porcentagem}</td>
              <td>
                <button onClick={() => openModal(cupom?.id)}>
                  <Pencil size={20} />
                </button>
              </td>
              <td>
                <button
                  onClick={() => {
                    deletar(cupom?.id);
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
            {Id ? "Editar" : "Cadastrar"} Cupom
          </h1>
          <Input
            placeholder="Nome"
            Label={"Nome"}
            onChange={(e) => setNome(e.target.value)}
            value={Nome}
            required
          />
          <Input
            type="number"
            placeholder="Porcentagem"
            Label={"Porcentagem"}
            onChange={(e) => setPorcentagem(e.target.value)}
            value={Porcentagem}
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
