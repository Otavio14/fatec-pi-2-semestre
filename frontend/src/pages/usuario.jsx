import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { api } from "../shared/api";
import { Trash, Pencil } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";

export const UsuarioPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [Usuarios, setUsuarios] = useState([]);
  const [Id, setId] = useState(0);
  const [Nome, setNome] = useState("");
  const [Email, setEmail] = useState("");
  const [Senha, setSenha] = useState("");
  const [ConfirmacaoSenha, setConfirmacaoSenha] = useState("");

  const salvar = (event) => {
    event.preventDefault();

    if (Senha !== ConfirmacaoSenha) return alert("As senhas não coincidem");

    const data = {
      nome: Nome,
      email: Email,
      senha: Senha,
    };

    api.post("/usuarios", data).then(() => {
      setShowModal(false);
      setReload((r) => !r);
      Toast.fire({
        title: "Usuário cadastrado com sucesso!",
        icon: "success",
      });
    });
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/usuarios/${id}`).then((response) => {
      setShowModal(true);
      setNome(response.data.nome);
      setEmail(response.data.email);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este usuário?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/usuarios/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Usuário deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setNome("");
    setEmail("");
    setSenha("");
    setConfirmacaoSenha("");
  };

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  useEffect(() => {
    api.get("/usuarios").then((response) => {
      setUsuarios(response.data);
    });
  }, [Reload]);

  return (
    <div className="flex w-full flex-col p-8">
      <div className="mb-[33px] flex w-full justify-between border-b border-[#d9d9d9] pb-[12px]">
        <h1 className="text-[38px] font-semibold leading-[140%]">Usuários</h1>
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
            <th>Email</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {Usuarios?.map((usuario) => (
            <tr key={usuario?.id}>
              <td>{usuario?.nome}</td>
              <td>{usuario?.email}</td>
              <td>
                <button onClick={() => openModal(usuario?.id)}>
                  <Pencil size={20} />
                </button>
              </td>
              <td>
                <button
                  onClick={() => {
                    deletar(usuario?.id);
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
          className="w-fir z-[15] mx-0 my-auto flex h-fit flex-col items-center gap-4 rounded-lg bg-[#f8f9ff] p-12"
        >
          <h1 className="text-[38px] font-semibold leading-[140%]">
            {Id ? "Editar" : "Cadastrar"} Usuário
          </h1>
          <Input
            type="text"
            placeholder="Nome"
            onChange={(e) => setNome(e.target.value)}
            value={Nome}
            required
          />
          <Input
            type="email"
            placeholder="Email"
            onChange={(e) => setEmail(e.target.value)}
            value={Email}
            required
          />
          <Input
            type="password"
            placeholder="Senha"
            onChange={(e) => setSenha(e.target.value)}
            value={Senha}
            required
          />
          <Input
            type="password"
            placeholder="Confirmação de Senha"
            onChange={(e) => setConfirmacaoSenha(e.target.value)}
            value={ConfirmacaoSenha}
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
