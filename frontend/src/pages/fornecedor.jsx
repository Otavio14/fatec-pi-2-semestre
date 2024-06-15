import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { api } from "../shared/api";
import { Trash, Pencil } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";
import { Select } from "../components/select";
import axios from "axios";

export const FornecedorPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [Fornecedores, setFornecedores] = useState([]);
  const [Id, setId] = useState(0);
  const [Nome, setNome] = useState("");
  const [Cep, setCep] = useState("");
  const [Endereco, setEndereco] = useState("");
  const [Complemento, setComplemento] = useState("");
  const [Telefone, setTelefone] = useState("");
  const [Estado, setEstado] = useState("");
  const [Cidade, setCidade] = useState("");
  const [Status, setStatus] = useState("");
  const [Estados, setEstados] = useState([]);
  const [Cidades, setCidades] = useState([]);
  const [NomeCidade, setNomeCidade] = useState("");

  const salvar = (event) => {
    event.preventDefault();

    const data = {
      nome: Nome,
      cep: Cep,
      endereco: Endereco,
      complemento: Complemento,
      telefone: Telefone,
      id_cidades: Cidade,
      status: Status,
    };

    if (Id) {
      api.put(`/fornecedores/${Id}`, data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Fornecedor alterada com sucesso!",
          icon: "success",
        });
      });
    } else {
      api.post("/fornecedores", data).then(() => {
        closeModal();
        setReload((r) => !r);
        Toast.fire({
          title: "Fornecedor cadastrado com sucesso!",
          icon: "success",
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/fornecedores/${id}`).then((response) => {
      setShowModal(true);
      setNome(response.data.nome);
      setEndereco(response.data.endereco);
      setCep(response.data.cep);
      setComplemento(response.data.complemento);
      setTelefone(response.data.telefone);
      setEstado(response.data.id_estados);
      setEstado(response.data.id_estados);
      setStatus(response.data.status);
    });
  };

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

      const { logradouro, localidade, uf } = response.data;
      setEndereco(logradouro);
      setEstado(Estados.find((estado) => estado.sigla === uf).id);
      setNomeCidade(localidade);
    });
  }, [Cep, Estados]);

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este fornecedor?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/fornecedores/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Fornecedor deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setNome("");
    setCep("");
    setEndereco("");
    setComplemento("");
    setTelefone("");
    setEstado("");
    setCidade("");
    setStatus("");
    setCidades([]);
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
  }, [Reload]);

  return (
    <div className="flex w-full flex-col p-8">
      <div className="mb-[33px] flex w-full justify-between border-b border-[#d9d9d9] pb-[12px]">
        <h1 className="text-[38px] font-semibold leading-[140%]">
          Fornecedores
        </h1>
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
            <th>Cidade</th>
            <th>Estado</th>
            <th>Telefone</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {Fornecedores?.map((fornecedor) => (
            <tr key={fornecedor?.id}>
              <td>{fornecedor?.nome}</td>
              <td>{fornecedor?.cidade?.nome}</td>
              <td>{fornecedor?.cidade?.estado?.sigla}</td>
              <td>{fornecedor?.telefone}</td>
              <td>
                <button onClick={() => openModal(fornecedor?.id)}>
                  <Pencil size={20} />
                </button>
              </td>
              <td>
                <button
                  onClick={() => {
                    deletar(fornecedor?.id);
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
            {Id ? "Editar" : "Cadastrar"} Fornecedor
          </h1>
          <Input
            placeholder="Nome"
            Label={"Nome"}
            onChange={(e) => setNome(e.target.value)}
            value={Nome}
            required
          />
          <Select
            Label={"Status"}
            onChange={(e) => setStatus(e.target.value)}
            value={Status}
          >
            <option>Selecione o status</option>
            <option>Ativa</option>
            <option>Inativa</option>
          </Select>
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
            placeholder="Endereço"
            Label={"Endereço"}
            onChange={(e) => setEndereco(e.target.value)}
            value={Endereco}
            required
          />
          <Input
            placeholder="Complemento"
            Label={"Complemento"}
            onChange={(e) => setComplemento(e.target.value)}
            value={Complemento}
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
