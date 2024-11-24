import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { api } from "../shared/api";
import { Trash, Eye } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";

export const AvaliacaoPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [Avaliacoes, setAvaliacoes] = useState([]);
  const [Comentario, setComentario] = useState("");

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar esta avaliação?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/avaliacoes/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Avaliação deletada com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const openModal = (comentario) => {
    setComentario(comentario);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setComentario("");
  };

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  useEffect(() => {
    api.get("/avaliacoes").then((response) => {
      setAvaliacoes(response.data);
    });
  }, [Reload]);

  return (
    <div className="flex max-h-screen w-full flex-col p-2 sm:p-8">
      <div className="mb-[33px] flex w-full flex-wrap justify-center gap-2 border-b border-[#d9d9d9] pb-[12px] sm:justify-between">
        <h1 className="text-[38px] font-semibold leading-[140%]">Avaliações</h1>
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
              <th>Cliente</th>
              <th>Nota</th>
              <th>Data</th>
              <th>Comentário</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {Avaliacoes?.map((avaliacao, index) => (
              <tr key={index}>
                <td>{avaliacao?.produto}</td>
                <td>{avaliacao?.cliente}</td>
                <td>{avaliacao?.nota}</td>
                <td>{avaliacao?.dtAvaliacao}</td>
                <td>
                  <button
                    onClick={() => {
                      openModal(avaliacao?.comentario);
                    }}
                  >
                    <Eye size={20} />
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => {
                      deletar(avaliacao?.id);
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
        <div className="w-fir z-[15] mx-0 my-auto flex h-fit flex-col items-center rounded-lg bg-[#f8f9ff] p-2 sm:p-12">
          <h1 className="text-center text-[38px] font-semibold leading-[140%]">
            Comentários
          </h1>
          <Input
            placeholder="Comentários..."
            readOnly
            Label={"Comentario"}
            value={Comentario}
            Textarea
          />
          <button
            type="button"
            className="w-fit rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
            onClick={closeModal}
          >
            Fechar
          </button>
        </div>
      </dialog>
    </div>
  );
};
