import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { Select } from "../components/select";
import { api } from "../shared/api";
import { Trash, Pencil } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";
import axios from "axios";

export const ClientePage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [Clientes, setClientes] = useState([]);
  const [Id, setId] = useState(0);
  const [Estados, setEstados] = useState([]);
  const [Cidades, setCidades] = useState([]);
  const [Nome, setNome] = useState("");
  const [Email, setEmail] = useState("");
  const [Telefone, setTelefone] = useState("");
  const [Cep, setCep] = useState("");
  const [Numero, setNumero] = useState(0);
  const [Estado, setEstado] = useState(0);
  const [Cidade, setCidade] = useState(0);
  const [Endereco, setEndereco] = useState("");
  const [Bairro, setBairro] = useState("");
  const [NomeCidade, setNomeCidade] = useState("");

  const salvar = (event) => {
    event.preventDefault();

    const data = {
      nome: Nome,
      id_cidades: +Cidade,
      cep: Cep,
      email: Email,
      telefone: Telefone,
      bairro: Bairro,
      numero: +Numero,
      endereco: Endereco,
    };

    if (Id) {
      api.put(`/clientes/${Id}`, data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Cliente alterado com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/clientes", data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Cliente cadastrado com sucesso!",
          icon: "success",
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/clientes/${id}`).then((response) => {
      setShowModal(true);
      setNome(response.data.nome);
      setEmail(response.data.email);
      setTelefone(response.data.telefone);
      setCep(response.data.cep);
      setNumero(response.data.numero);
      setEstado(response.data.cidade.estado.id);
      setNomeCidade(response.data.cidade.nome);
      setEndereco(response.data.endereco);
      setBairro(response.data.bairro);
      setCidade(response.data.cidade.id);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este cliente?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api
          .delete(`/clientes/${id}`)
          .then(() => {
            setReload((r) => !r);
            Toast.fire({
              title: "Cliente deletado com sucesso!",
              icon: "success",
            });
          })
          .catch((e) => {
            Swal.fire({
              title: "Não foi possível deletar o cliente!",
              text: e.response.data.message,
              icon: "error",
            });
          });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setNome("");
    setEmail("");
    setCidades([]);
    setTelefone("");
    setCep("");
    setNumero(0);
    setEstado(0);
    setCidade(0);
    setEndereco("");
    setBairro("");
    setNomeCidade("");
  };

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  useEffect(() => {
    api.get("/clientes").then((response) => {
      setClientes(response.data);
    });
  }, [Reload]);

  useEffect(() => {
    api.get("/estados").then((response) => {
      setEstados(response.data);
    });
  }, []);

  useEffect(() => {
    if (!Estado) return;

    api.get(`/cidades/estado/${Estado}`).then((response) => {
      setCidades(response.data);
      setCidade(response.data.find((cidade) => cidade.nome === NomeCidade).id);
    });
  }, [Estado, NomeCidade]);

  useEffect(() => {
    if (Cep.length !== 9) return;

    axios.get(`https://viacep.com.br/ws/${Cep}/json/`).then((response) => {
      if (response.data.erro) return;

      const { logradouro, bairro, localidade, uf } = response.data;
      setEndereco(logradouro);
      setBairro(bairro);
      setEstado(Estados.find((estado) => estado.sigla === uf).id);
      setNomeCidade(localidade);
    });
  }, [Cep, Estados]);

  return (
    <div className="flex max-h-screen w-full flex-col p-2 sm:p-8">
      <div className="mb-[33px] flex w-full flex-wrap justify-center gap-2 border-b border-[#d9d9d9] pb-[12px] sm:justify-between">
        <h1 className="text-[38px] font-semibold leading-[140%]">Clientes</h1>
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
              <th>Email</th>
              <th>Telefone</th>
              <th>Cidade</th>
              <th>Estado</th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {Clientes?.map((cliente) => (
              <tr key={cliente?.id}>
                <td>{cliente?.nome}</td>
                <td>{cliente?.email}</td>
                <td>{cliente?.telefone}</td>
                <td>{cliente?.cidade?.nome}</td>
                <td>{cliente?.cidade?.estado?.sigla}</td>
                <td>
                  <button onClick={() => openModal(cliente?.id)}>
                    <Pencil size={20} />
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => {
                      deletar(cliente?.id);
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
            {Id ? "Editar" : "Cadastrar"} Cliente
          </h1>
          <div className="grid grid-cols-1 gap-x-4 sm:grid-cols-3">
            <Input
              Label="Nome"
              name="nome"
              placeholder="Nome Completo"
              value={Nome}
              onChange={(e) => setNome(e.target.value)}
              required
            />
            <Input
              Label="E-mail"
              type="email"
              name="email"
              placeholder="E-mail"
              value={Email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            <Input
              Label="Telefone"
              type="tel"
              name="telefone"
              placeholder="(00) 00000-0000"
              value={Telefone}
              onChange={(e) =>
                setTelefone(
                  e.target.value
                    .replace(/\D/g, "")
                    .replace(/(\d{2})(\d)/, "($1) $2")
                    .replace(/(\d{5})(\d)/, "$1-$2")
                    .slice(0, 15),
                )
              }
              required
            />
            <Input
              Label="CEP"
              name="cep"
              placeholder="00000-000"
              value={Cep}
              onChange={(e) =>
                setCep(
                  e.target.value
                    .replace(/\D/g, "")
                    .replace(/(\d{5})(\d)/, "$1-$2")
                    .slice(0, 9),
                )
              }
              required
            />
            <Select
              Label={"Estado"}
              onChange={(e) => setEstado(e.target.value)}
              value={Estado}
            >
              <option>Selecione o estado</option>
              {Estados.map((estado) => (
                <option key={estado.id} value={estado.id}>
                  {estado.sigla} - {estado.nome}
                </option>
              ))}
            </Select>
            <Select
              Label={"Cidade"}
              onChange={(e) => setCidade(e.target.value)}
              value={Cidade}
            >
              <option>Selecione a cidade</option>
              {Cidades.map((cidade) => (
                <option key={cidade.id} value={cidade.id}>
                  {cidade.nome}
                </option>
              ))}
            </Select>
            <Input
              Label="Endereço"
              name="endereco"
              placeholder="Endereço"
              value={Endereco}
              onChange={(e) => setEndereco(e.target.value)}
              required
            />
            <Input
              Label="Número"
              type="number"
              name="numero"
              placeholder="Número"
              value={Numero}
              onChange={(e) => setNumero(e.target.value)}
              required
            />
            <Input
              Label="Bairro"
              name="bairro"
              placeholder="Bairro"
              value={Bairro}
              onChange={(e) => setBairro(e.target.value)}
              required
            />
          </div>
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
