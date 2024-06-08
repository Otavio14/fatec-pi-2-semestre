import { useEffect, useState } from "react";
import { Input } from "../components/input.jsx";
import { Select } from "../components/select.jsx";
import { api } from "../shared/api.js";
import axios from "axios";

export const FinalizarCompraPage = () => {
  const [CarrinhoTotal, setCarrinhoTotal] = useState(0);
  const [CarrinhoProdutos, setCarrinhoProdutos] = useState([]);
  const [Confirmacao, setConfirmacao] = useState(false);
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

  const finalizarCompra = (e) => {
    e.preventDefault();

    const produtos = CarrinhoProdutos.map((produto) => ({
      id: produto.id,
      quantidade: produto.quantidade,
    }));

    const cliente = {
      nome: Nome,
      email: Email,
      telefone: Telefone,
      cep: Cep,
      numero: Numero,
      estado: Estado,
      cidade: Cidade,
      endereco: Endereco,
      bairro: Bairro,
    };

    api.post("/clientes", cliente).then((response) => {
      if (response.status === 201) {
        localStorage.removeItem("carrinho");
        setCarrinhoProdutos([]);
        setCarrinhoTotal(0);
        setConfirmacao(false);
        alert("Compra realizada com sucesso!");
      }
    });
  };

  useEffect(() => {
    const local = JSON.parse(localStorage.getItem("carrinho") || "{}");

    if (!local || !local.produtos || !local.total || !local.quantidade) return;

    const { produtos, total } = local;
    setCarrinhoProdutos(produtos);
    setCarrinhoTotal(total);

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
    <div className="flex flex-col items-center px-4 py-10 sm:px-20">
      <h1 className="mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] text-[38px] font-semibold leading-[140%]">
        Finalizar Compra
      </h1>
      {/* Lista de Produtos */}
      <div
        className={`flex w-full flex-col items-center ${Confirmacao ? "hidden" : "flex"}`}
      >
        <div className="w-full bg-white">
          {CarrinhoProdutos.map((produto, index) => (
            <div
              key={index}
              className="grid w-full grid-cols-[auto,1fr,66px,100px] items-center gap-4 border-b border-[#d9d9d9] p-4"
            >
              <div className="flex h-[100px] w-[100px] items-center justify-center rounded border p-[10px]">
                <img
                  src={produto.imagem}
                  alt={produto.nome}
                  className="h-full w-full max-w-[70px] object-contain"
                />
              </div>
              <h2 className="text-[18px] font-semibold leading-[140%]">
                {produto.nome}
              </h2>
              <input
                readOnly
                value={produto.quantidade}
                type="number"
                className="h-[28px] w-[66px] rounded border border-[#8f9eb2] px-[10px]"
              />
              <p className="text-[18px] font-semibold leading-[140%]">
                {produto.preco.toLocaleString("pt-br", {
                  style: "currency",
                  currency: "BRL",
                })}
              </p>
            </div>
          ))}
        </div>
        <h2 className="mt-[20px] self-end text-[24px] font-semibold leading-[140%]">
          Total:{" "}
          {CarrinhoTotal.toLocaleString("pt-br", {
            style: "currency",
            currency: "BRL",
          })}
        </h2>
        <button
          onClick={() => setConfirmacao(true)}
          className="w-fit rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
        >
          Continuar
        </button>
      </div>
      {/* Formulário sobre o cliente */}
      <form
        className={`w-full flex-col ${Confirmacao ? "flex" : "hidden"}`}
        onSubmit={finalizarCompra}
      >
        <h2 className="mt-[20px] w-full pb-[12px] text-center text-[24px] font-semibold leading-[140%]">
          Informações do Cliente
        </h2>
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
                  .slice(0, 14),
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
        <div className="mt-8 flex items-center justify-center gap-4 sm:gap-20">
          <button
            onClick={() => setConfirmacao(false)}
            type="button"
            className="w-fit rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
          >
            Voltar
          </button>
          <button
            className="w-fit rounded border bg-[#2B38D1] px-[34px] py-[15px] font-semibold leading-[20px] text-white hover:bg-white hover:text-[#0c2d57]"
            type="submit"
          >
            Finalizar Compra
          </button>
        </div>
      </form>
    </div>
  );
};
