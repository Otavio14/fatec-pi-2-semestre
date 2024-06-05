import { useEffect, useRef, useState } from "react";
import { Card } from "../components/card.jsx";
import { NavLink } from "react-router-dom";
import { api } from "../shared/api.js";
import { Input } from "../components/input.jsx";

export const ProdutosPage = () => {
    const [Produtos, setProdutos] = useState([]);
    const [NomeProduto, setNomeProduto] = useState("");
    const [PrecoProduto, setPrecoProduto] = useState(0);
    const [EstoqueProduto, setEstoqueProduto] = useState(0);
    const [DescricaoProduto, setDescricaoProduto] = useState("");
    const [ValidadeProduto, setValidadeProduto] = useState("");
    const [ImagemProduto, setImagemProduto] = useState("");

    const data = {
        nome: NomeProduto,
        preco: parseFloat(PrecoProduto),
        estoque: parseInt(EstoqueProduto),
        descricao: DescricaoProduto,
        dt_validade: ValidadeProduto,
        imagem: ImagemProduto,
    }

    useEffect(() => {
        api.get("/produtos").then((res) => {
            setProdutos(res.data)
        })
    }, []);

    const DialogRef = useRef();
    const [ShowModal, setShowModal] = useState(false);

    const salvar = (event) => {
        event.preventDefault();

        api.post("/produtos", data).then((res) => console.log(res))

        // setShowModal(false);
    };

    useEffect(() => {
        if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
            DialogRef?.current?.showModal();
        else DialogRef?.current?.close();
    }, [ShowModal]);

    return (
        <div className="flex flex-col items-center p-20">
            <div className="mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] flex justify-between p-8">
                <h1 className="text-[38px] leading-[140%] font-semibold">Produtos</h1>
                <button
                    className="rounded bg-[#dd3842] py-[15px] px-[34px] font-semibold leading-[20px] text-white w-fit"
                    onClick={() => setShowModal(true)}
                >
                    Adicionar
                </button>
            </div>
            <dialog
                ref={DialogRef}
                onCancel={() => setShowModal(false)}
                className={`backdrop:bg-black/50 bg-transparent flex-col p-4 fixed left-0 top-0 h-screen w-screen border-none items-center z-[13] overflow-y-auto ${ShowModal ? "flex" : ""
                    }`}
            >
                <form
                    onSubmit={salvar}
                    className="bg-[#f8f9ff] flex flex-col gap-4 rounded-lg items-center h-fit my-auto mx-0 p-12 w-fir z-[15]"
                >
                    <h1 className="text-[38px] leading-[140%] font-semibold">
                        Cadastrar Usuário
                    </h1>
                    <Input type="text" placeholder="Nome" onChange={(e) => setNomeProduto(e.target.value)} />
                    <Input type="number" placeholder="Preço" onChange={(e) => setPrecoProduto(e.target.value)} />
                    <Input type="number" placeholder="Qtde em estoque" onChange={(e) => setEstoqueProduto(e.target.value)} />
                    <Input type="text" placeholder="Descrição" onChange={(e) => setDescricaoProduto(e.target.value)} />
                    <Input type="text" placeholder="Data de Validade" onChange={(e) => setValidadeProduto(e.target.value)} />
                    <Input type="text" placeholder="Imagem URL" onChange={(e) => setImagemProduto(e.target.value)} />
                    <div className="flex gap-4">
                        <button className="rounded bg-[#dd3842] hover:bg-white hover:text-[#0c2d57] hover:border-[#0c2d57] border py-[15px] px-[34px] font-semibold leading-[20px] text-white w-fit">
                            Cadastrar
                        </button>
                        <button
                            className="rounded border-[#0c2d57] border bg-white hover:bg-[#dd3842] hover:text-white hover:border-white py-[15px] px-[34px] font-semibold leading-[20px] text-[#0c2d57] w-fit"
                            onClick={() => setShowModal(false)}
                        >
                            Cancelar
                        </button>
                    </div>
                </form>
            </dialog>
            <div className="grid grid-cols-1 gap-[20px] md:grid-cols-2 xl:grid-cols-3">
                {Produtos.map((produto, i) => (
                    <NavLink key={i} to={`/produto/${produto?.id}`}>
                        <Card
                            Text={produto.nome}
                            Price={produto.preco}
                            Image={produto.imagem}
                        // Nota={produto.Nota} Para evitar confusão por enquanto
                        />
                    </NavLink>
                ))}
            </div>
        </div>
    );
};
