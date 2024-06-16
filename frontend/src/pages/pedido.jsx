import { useEffect, useRef, useState } from "react";
import { Input } from "../components/input";
import { api } from "../shared/api";
import { Trash, Pencil, Plus } from "@phosphor-icons/react";
import { Swal, Toast } from "../shared/swal";
import { Select } from "../components/select";

export const PedidoPage = () => {
  const DialogRef = useRef();
  const [ShowModal, setShowModal] = useState(false);
  const [Reload, setReload] = useState(false);
  const [Pedidos, setPedidos] = useState([]);
  const [Id, setId] = useState(0);
  const [Status, setStatus] = useState("");
  const [Cliente, setCliente] = useState(0);
  const [Endereco, setEndereco] = useState("");
  const [DtPedido, setDtPedido] = useState("");
  const [Total, setTotal] = useState(0);
  const [ProdutosPedidos, setProdutosPedidos] = useState([]);
  const [Clientes, setClientes] = useState([]);
  const [Produtos, setProdutos] = useState([]);

  const salvar = (event) => {
    event.preventDefault();

    if (ProdutosPedidos.length === 0) {
      Swal.fire({
        title: "Adicione produtos ao pedido!",
        icon: "error",
        target: DialogRef.current,
      });
      return;
    }

    const data = {
      id_clientes: Number(Cliente),
      status: Status,
      endereco: Endereco,
      dt_pedido: DtPedido,
      total: Number(Total),
    };

    if (Id) {
      api.put(`/pedidos/${Id}`, data).then((res) => {
        const pedidos_produtos = ProdutosPedidos?.map((produto) => ({
          id_pedidos: Number(res.data.id),
          id_produtos: Number(produto?.id_produtos),
          quantidade: Number(produto?.quantidade),
          preco: Number(produto?.preco),
        }));

        api.post("/produtos-pedidos/multiple", pedidos_produtos).then(() => {
          closeModal();
          setReload((r) => !r);
          Toast.fire({
            title: "Pedido alterado com sucesso!",
            icon: "success",
          });
        });
      });
    } else {
      api.post("/pedidos", data).then((res) => {
        const pedidos_produtos = ProdutosPedidos?.map((produto) => ({
          id_pedidos: Number(res.data.id),
          id_produtos: Number(produto?.id_produtos),
          quantidade: Number(produto?.quantidade),
          preco: Number(produto?.preco),
        }));

        api.post("/produtos-pedidos/multiple", pedidos_produtos).then(() => {
          closeModal();
          setReload((r) => !r);
          Toast.fire({
            title: "Pedido cadastrado com sucesso!",
            icon: "success",
          });
        });
      });
    }
  };

  const openModal = (id) => {
    setId(id ? id : 0);

    api.get(`/pedidos/${id}`).then((response) => {
      setShowModal(true);
      setStatus(response.data.status);
      setCliente(response.data.id_clientes);
      setEndereco(response.data.endereco);
      setDtPedido(
        new Date(response.data.dt_pedido).toISOString().substring(0, 16),
      );
      setProdutosPedidos(response.data.ProdutoPedido);
      setTotal(response.data.total);
    });
  };

  const deletar = (id) => {
    Swal.fire({
      title: "Deseja deletar este pedido?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim",
      cancelButtonText: "Não",
    }).then(({ isConfirmed }) => {
      if (isConfirmed)
        api.delete(`/pedidos/${id}`).then(() => {
          setReload((r) => !r);
          Toast.fire({
            title: "Pedido deletado com sucesso!",
            icon: "success",
          });
        });
    });
  };

  const closeModal = () => {
    setShowModal(false);
    setId(0);
    setStatus("");
    setCliente(0);
    setEndereco("");
    setDtPedido("");
    setTotal(0);
  };

  const adicionarProduto = (produto) => {
    setProdutosPedidos((p) => {
      const index = p.findIndex((f) => f?.id_produtos === produto?.id);

      if (index >= 0) {
        return [
          ...p.slice(0, index),
          { ...p[index], quantidade: p[index].quantidade + 1 },
          ...p.slice(index + 1),
        ];
      } else {
        const produtoPedido = {
          id_pedidos: Id,
          id_produtos: produto?.id,
          preco: produto?.preco,
          quantidade: 1,
          produto: {
            id: produto?.id,
            nome: produto?.nome,
            preco: produto?.preco,
          },
        };
        return [...p, produtoPedido];
      }
    });
  };

  const removerProduto = (produto) => {
    setProdutosPedidos((p) => {
      const qtde = p.find(
        (f) => f?.id_produtos === produto?.id_produtos,
      )?.quantidade;
      const index = p.findIndex((f) => f?.id_produtos === produto?.id);

      if (qtde > 1) {
        return [
          ...p.slice(0, index),
          { ...p[index], quantidade: p[index].quantidade - 1 },
          ...p.slice(index + 1),
        ];
      } else {
        return p.filter((f) => f?.id_produtos !== produto?.id_produtos);
      }
    });
  };

  useEffect(() => {
    if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
      DialogRef?.current?.showModal();
    else DialogRef?.current?.close();
  }, [ShowModal]);

  useEffect(() => {
    api.get("/pedidos").then((response) => {
      setPedidos(response.data);
    });
  }, [Reload]);

  useEffect(() => {
    api.get("/clientes").then((response) => {
      setClientes(response.data);
    });
    api.get("/produtos").then((response) => {
      setProdutos(response.data);
    });
  }, []);

  useEffect(() => {
    setTotal(() => {
      return ProdutosPedidos.reduce(
        (acc, cur) => acc + cur.preco * cur.quantidade,
        0,
      );
    });
  }, [ProdutosPedidos]);

  return (
    <div className="flex w-full flex-col p-8">
      <div className="mb-[33px] flex w-full justify-between border-b border-[#d9d9d9] pb-[12px]">
        <h1 className="text-[38px] font-semibold leading-[140%]">Pedidos</h1>
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
            <th>Cliente</th>
            <th>Data</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {Pedidos?.map((pedido) => (
            <tr key={pedido?.id}>
              <td>{pedido?.cliente?.nome}</td>
              <td>
                {new Date(pedido?.dt_pedido).toLocaleDateString("pt-BR", {
                  hour: "2-digit",
                  minute: "2-digit",
                })}
              </td>
              <td>
                <button onClick={() => openModal(pedido?.id)}>
                  <Pencil size={20} />
                </button>
              </td>
              <td>
                <button
                  onClick={() => {
                    deletar(pedido?.id);
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
            {Id ? "Editar" : "Cadastrar"} Pedido
          </h1>
          <Select
            Label={"Cliente"}
            onChange={(e) => setCliente(e.target.value)}
            value={Cliente}
          >
            <option>Selecione o estado</option>
            {Clientes.map((m) => (
              <option key={m.id} value={m.id}>
                {m.nome}
              </option>
            ))}
          </Select>
          <Input
            placeholder="Status"
            Label={"Status"}
            onChange={(e) => setStatus(e.target.value)}
            value={Status}
            required
          />
          <Input
            placeholder="Endereço"
            Label={"Endereço"}
            onChange={(e) => setEndereco(e.target.value)}
            value={Endereco}
            required
          />
          <Input
            Label={"Data Pedido"}
            type="datetime-local"
            onChange={(e) => setDtPedido(e.target.value)}
            value={DtPedido}
            required
          />
          <Input
            placeholder="Total"
            Label={"Total"}
            onChange={(e) => setTotal(e.target.value)}
            value={Total}
            type="number"
            required
            readOnly
          />
          <h3 className="text-[28px] font-semibold leading-[140%]">Produtos</h3>
          <div className="my-4 grid grid-cols-1 gap-2 overflow-x-auto lg:grid-cols-2">
            <table className="h-fit border-collapse border bg-white">
              <thead>
                <tr>
                  <th colSpan="5" className="text-center">
                    Disponíveis
                  </th>
                </tr>
                <tr>
                  <th>Produto</th>
                  <th>Preço</th>
                  <th>Estoque</th>
                  <th>Validade</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {Produtos?.map((produto, index) => (
                  <tr key={index}>
                    <td>{produto?.nome}</td>
                    <td>R$ {produto?.preco?.toFixed(2)}</td>
                    <td>{produto?.estoque}</td>
                    <td>{produto?.dt_validade}</td>
                    <td>
                      <button
                        onClick={() => adicionarProduto(produto)}
                        type="button"
                      >
                        <Plus size={20} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <table className="h-fit border-collapse border bg-white">
              <thead>
                <tr>
                  <th colSpan="4" className="text-center">
                    Selecionados
                  </th>
                </tr>
                <tr>
                  <th>Produto</th>
                  <th>Quantidade</th>
                  <th>Preço</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {ProdutosPedidos?.map((produto, index) => (
                  <tr key={index}>
                    <td>{produto?.produto?.nome}</td>
                    <td>{produto?.quantidade}</td>
                    <td>R$ {produto?.preco?.toFixed(2)}</td>
                    <td>
                      <button
                        onClick={() => removerProduto(produto)}
                        type="button"
                      >
                        <Trash size={20} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
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
