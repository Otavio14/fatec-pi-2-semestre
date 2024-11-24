import axios from "axios";
import React, { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { api } from "../shared/api";
import { Input } from "./input";
import { Select } from "./select";

export const EnderecoModal = ({
  show,
  setShow,
  hasChanged,
  setHasChanged,
  cep,
  bairro,
  rua,
  numero,
  estado,
  idCidade,
  updateFieldData,
  confirmChange,
}) => {
  const [Estados, setEstados] = useState([]);
  const [Cidades, setCidades] = useState([]);

  const [Cep, setCep] = useState(cep || "");
  const [Numero, setNumero] = useState(numero || "");
  const [Estado, setEstado] = useState(estado || 0);
  const [Cidade, setCidade] = useState(idCidade || 0);
  const [Endereco, setEndereco] = useState(rua || "");
  const [Bairro, setBairro] = useState(bairro || "");
  const [NomeCidade, setNomeCidade] = useState("");

  useEffect(() => {
    if (show) {
      setCep(cep || "");
      setNumero(numero || "");
      setEstado(estado || 0);
      setCidade(idCidade || 0);
      setEndereco(rua || "");
      setBairro(bairro || "");
    }
  }, [show, cep, numero, estado, idCidade, rua, bairro]);

  useEffect(() => {
    api.get("/estados").then((response) => {
      setEstados(response.data);
    });
  }, []);

  useEffect(() => {
    if (Cep.length !== 9 || Estados.length === 0) return;

    axios.get(`https://viacep.com.br/ws/${Cep}/json/`).then((response) => {
      if (response.data.erro) return;

      const { logradouro, bairro, localidade, uf } = response.data;
      setEndereco(logradouro);
      setBairro(bairro);
      const estadoCorrespondente = Estados.find((estado) => estado.sigla === uf);
      if (estadoCorrespondente) {
        setEstado(estadoCorrespondente.id);
        setNomeCidade(localidade);
        api.get(`/cidades/estado/${Estado}`).then((response) => {
          setCidades(response.data);
        });
      }
    });
  }, [Cep, Estados]);

  useEffect(() => {
    if (!Estado) return;

    api.get(`/cidades/estado/${Estado}`).then((response) => {
      setCidades(response.data);
    });
  }, [Estado]);

  useEffect(() => {
    if (idCidade && Cidades.length > 0) {
      const cidadeInicial = Cidades.find((cidade) => cidade.id === idCidade);
      if (cidadeInicial) {
        setCidade(cidadeInicial.id);
        setNomeCidade(cidadeInicial.nome);
      }
    }
  }, [idCidade, Cidades]);

  return (
    <div
      className={`${
        show
          ? "flex fixed inset-0 justify-center items-center bg-black bg-opacity-30 backdrop-blur-md transition-opacity duration-300"
          : "hidden"
      }`}
    >
      <div
        className="flex flex-col w-full max-w-[90%] sm:max-w-[500px] bg-white shadow-xl p-6 sm:p-8 rounded-3xl relative max-h-[90vh] overflow-y-auto"
      >
        <button
          className="absolute top-4 right-4 text-white bg-red-500 hover:bg-red-600 px-4 py-1 rounded-full shadow-lg transition-all duration-200"
          onClick={() => setShow(!show)}
        >
          X
        </button>

        <h2 className="text-2xl font-bold mb-6 text-center text-gray-700">
          Endereço
        </h2>

        <Input
          Label={"CEP"}
          value={Cep}
          placeholder="00000-000"
          onChange={(e) => {
            const novoCep = e.target.value
              .replace(/\D/g, "")
              .replace(/(\d{5})(\d)/, "$1-$2")
              .slice(0, 9);
            updateFieldData("cep", novoCep);
            setCep(novoCep);
          }}
          required
          type="text"
          autoComplete="text"
        />

        <Select
          Label={"Estado"}
          onChange={(e) => setEstado(e.target.value)}
          value={Estado || ""}
        >
          <option value="">Selecione o estado</option>
          {Estados.map((estado) => (
            <option key={estado.id} value={estado.id}>
              {estado.sigla} - {estado.nome}
            </option>
          ))}
        </Select>

        <Select
          Label={"Cidade"}
          onChange={(e) => setCidade(e.target.value)}
          value={Cidade || ""}
        >
          <option value="">Selecione a cidade</option>
          {Cidades.map((cidade) => (
            <option key={cidade.id} value={cidade.id}>
              {cidade.nome}
            </option>
          ))}
        </Select>

        <Input
          Label={"Bairro"}
          value={Bairro || ""}
          placeholder={"Digite seu bairro"}
          onChange={(e) => updateFieldData("bairro", e.target.value)}
          required
          type="text"
          autoComplete="text"
        />

        <Input
          Label={"Rua"}
          value={Endereco}
          placeholder={"Digite sua rua"}
          onChange={(e) => updateFieldData("endereco", e.target.value)}
          required
          type="text"
          autoComplete="text"
        />

        <Input
          Label={"Número"}
          value={Numero || ""}
          placeholder={"Digite seu número"}
          onChange={(e) => updateFieldData("numero", parseInt(e.target.value))}
          required
          type="text"
          autoComplete="text"
        />

        <button
          className={`mt-6 px-6 py-2 rounded-xl font-semibold shadow-lg transition-all duration-200 ${
            hasChanged
              ? "bg-blue-500 text-white hover:bg-blue-600"
              : "bg-gray-300 text-gray-600 cursor-not-allowed"
          }`}
          disabled={!hasChanged}
          onClick={() => {
            if (confirmChange()) {
              Swal.fire({
                icon: "error",
                title: "Erro!",
                text: "Algo deu errado!",
              });
            } else {
              Swal.fire({
                icon: "success",
                title: "Alterado!",
                text: "Informações alteradas!",
              });
              setShow(!show);
              setHasChanged(false);
            }
          }}
        >
          Salvar
        </button>
      </div>
    </div>
  );
};
