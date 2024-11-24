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
      idCliente: Number(Cliente),
      status: Status,
      endereco: Endereco,
      dtPedido: DtPedido,
      total: Number(Total),
    };

    if (Id) {
      api.put(`/pedidos/${Id}`, data).then((res) => {
        const pedidos_produtos = ProdutosPedidos?.map((produto) => ({
          idPedido: Number(res.data.id),
          idProduto: Number(produto?.idProduto),
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
          idPedido: Number(res.data),
          idProduto: Number(produto?.idProduto),
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
      setCliente(response.data.idCliente);
      setEndereco(response.data.endereco);
      setDtPedido(
        new Date(response.data.dtPedido).toISOString().substring(0, 16),
      );
      setTotal(response.data.total);

      api.get(`/produtos-pedidos/pedido/${id}`).then((res_produtos) => {
        setProdutosPedidos(res_produtos.data);
      });
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
      const index = p.findIndex((f) => f?.idProduto === produto?.id);

      if (index >= 0) {
        return [
          ...p.slice(0, index),
          { ...p[index], quantidade: p[index].quantidade + 1 },
          ...p.slice(index + 1),
        ];
      } else {
        const produtoPedido = {
          idPedido: Id,
          idProduto: produto?.id,
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
        (f) => f?.idProduto === produto?.idProduto,
      )?.quantidade;
      const index = p.findIndex((f) => f?.idProduto === produto?.id);

      if (qtde > 1) {
        return [
          ...p.slice(0, index),
          { ...p[index], quantidade: p[index].quantidade - 1 },
          ...p.slice(index + 1),
        ];
      } else {
        return p.filter((f) => f?.idProduto !== produto?.idProduto);
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
    <div className="flex min-h-screen w-full flex-col bg-gray-100 p-4 sm:p-8">
      <div className="mb-8 flex w-full flex-wrap items-center justify-between gap-4 border-b border-gray-300 pb-4">
        <h1 className="text-3xl font-bold text-gray-800">Pedidos</h1>
        <button
          className="rounded bg-red-600 px-6 py-3 text-lg font-semibold text-white shadow hover:bg-red-500"
          onClick={() => setShowModal(true)}
        >
          + Cadastrar Pedido
        </button>
      </div>

      <div className="w-full overflow-x-auto rounded-lg bg-white shadow-lg">
        <table className="w-full border-collapse text-left text-gray-700">
          <thead className="bg-gray-200">
            <tr>
              <th className="px-6 py-4">Cliente</th>
              <th className="px-6 py-4">Data</th>
              <th className="px-6 py-4 text-center">Editar</th>
              <th className="px-6 py-4 text-center">Deletar</th>
            </tr>
          </thead>
          <tbody>
            {Pedidos?.map((pedido) => (
              <tr
                key={pedido?.id}
                className="border-t hover:bg-gray-50 transition"
              >
                <td className="px-6 py-4">{pedido?.cliente?.nome}</td>
                <td className="px-6 py-4">
                  {new Date(pedido?.dtPedido).toLocaleDateString("pt-BR", {
                    hour: "2-digit",
                    minute: "2-digit",
                  })}
                </td>
                <td className="px-6 py-4 text-center">
                  <button
                    onClick={() => openModal(pedido?.id)}
                    className="text-blue-600 hover:text-blue-800"
                  >
                    <Pencil size={20} />
                  </button>
                </td>
                <td className="px-6 py-4 text-center">
                  <button
                    onClick={() => deletar(pedido?.id)}
                    className="text-red-600 hover:text-red-800"
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
        className={`fixed inset-0 z-40 flex items-center justify-center bg-black bg-opacity-50 backdrop-blur ${ShowModal ? "flex" : "hidden"
          }`}
      >
        <form
          onSubmit={salvar}
          className="w-full max-w-3xl max-h-[90vh] overflow-y-auto rounded-lg bg-white p-6 shadow-xl sm:p-12"
        >
          <h1 className="mb-6 text-center text-2xl font-bold text-gray-800">
            {Id ? "Editar" : "Cadastrar"} Pedido
          </h1>
          <Select
            Label={"Cliente"}
            onChange={(e) => setCliente(e.target.value)}
            value={Cliente}
          >
            <option>Selecione o cliente</option>
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
          <h3 className="mt-8 text-xl font-semibold text-gray-800">Produtos</h3>
          <div className="my-4 flex justify-center">
            <table className="w-full max-w-4xl border-collapse rounded-lg bg-white shadow-lg text-gray-700">
              <thead className="bg-gray-200">
                <tr>
                  <th className="px-6 py-4">Produto</th>
                  <th className="px-6 py-4">Quantidade</th>
                  <th className="px-6 py-4">Preço</th>
                  <th className="px-6 py-4 text-center">Remover</th>
                </tr>
              </thead>
              <tbody>
                {ProdutosPedidos?.map((produto, index) => (
                  <tr key={index} className="border-t hover:bg-gray-50 transition">
                    <td className="px-6 py-4">{produto?.produto?.nome}</td>
                    <td className="px-6 py-4">{produto?.quantidade}</td>
                    <td className="px-6 py-4">R$ {produto?.preco?.toFixed(2)}</td>
                    <td className="px-6 py-4 text-center">
                      <button
                        onClick={() => removerProduto(produto)}
                        type="button"
                        className="text-red-600 hover:text-red-800"
                      >
                        <Trash size={20} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="mt-6 flex justify-end gap-4">
            <button
              className="rounded bg-blue-600 px-6 py-3 text-white shadow hover:bg-blue-500"
              type="submit"
            >
              Salvar
            </button>
            <button
              type="button"
              className="rounded bg-gray-400 px-6 py-3 text-white shadow hover:bg-gray-300"
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
