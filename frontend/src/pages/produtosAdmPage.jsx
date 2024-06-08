import { useEffect, useRef, useState } from "react";
import { Card } from "../components/card.jsx";
import { NavLink } from "react-router-dom";
import { api } from "../shared/api.js";
import { Input } from "../components/input.jsx";
import Swal from "sweetalert2";

export const ProdutosPage = () => {
    const [Produtos, setProdutos] = useState([]);
    const [NomeProduto, setNomeProduto] = useState("");
    const [PrecoProduto, setPrecoProduto] = useState(Number);
    const [EstoqueProduto, setEstoqueProduto] = useState(Number);
    const [DescricaoProduto, setDescricaoProduto] = useState("");
    const [ValidadeProduto, setValidadeProduto] = useState("");
    const [ImagemProduto, setImagemProduto] = useState("");

    const verificaNull = () => {
        if (NomeProduto === "" || PrecoProduto === 0 || EstoqueProduto === 0 || DescricaoProduto === "" || ValidadeProduto === "" || ImagemProduto === "") {
            return false
        } else {
            return true
        }
    }

    const setaFalse = () => {
        setNomeProduto("")
        setPrecoProduto("")
        setEstoqueProduto("")
        setDescricaoProduto("")
        setValidadeProduto("")
        setImagemProduto("")
    }

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
        const verify = verificaNull();
        if (verify == true) {
            api.post("/produtos", data).then((res) => console.log(res))
        }
        else if (verify == false) {
            Swal.fire({
                title: 'Faltam informações',
                text: "Preencha todos os campos",
                icon: "warning"
            })
        }
        setShowModal(false);
        setaFalse();
    };

    useEffect(() => {
        if (ShowModal && !DialogRef?.current?.hasAttribute("open"))
            DialogRef?.current?.showModal();
        else DialogRef?.current?.close();
    }, [ShowModal]);

    return (
        <div className="flex flex-col items-center overflow-hidden h-screen">
            <div className=" overflow-hidden mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] flex justify-between p-8">
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
                    className="bg-[#f8f9ff] flex flex-col gap-4 rounded-lg items-center h-fit my-auto mx-0 p-12 w-fir z-[15]"
                >
                    <h1 className="text-[38px] leading-[140%] font-semibold">
                        Cadastrar Produto
                    </h1>
                    <Input type="text" placeholder="Nome" value={NomeProduto} onChange={(e) => setNomeProduto(e.target.value)} />
                    <Input type="number" placeholder="Preço" value={PrecoProduto} onChange={(e) => setPrecoProduto(e.target.value)} />
                    <Input type="number" placeholder="Qtde em estoque" value={EstoqueProduto} onChange={(e) => setEstoqueProduto(e.target.value)} />
                    <Input type="text" placeholder="Descrição" value={DescricaoProduto} onChange={(e) => setDescricaoProduto(e.target.value)} />
                    <Input type="text" placeholder="Data de Validade" value={ValidadeProduto} onChange={(e) => setValidadeProduto(e.target.value)} />
                    <Input type="text" placeholder="Imagem URL" value={ImagemProduto} onChange={(e) => setImagemProduto(e.target.value)} />
                    <div className="flex gap-4">
                        <button onClick={(e) => salvar(e)} className="rounded bg-[#dd3842] hover:bg-white hover:text-[#0c2d57] hover:border-[#0c2d57] border py-[15px] px-[34px] font-semibold leading-[20px] text-white w-fit">
                            Cadastrar
                        </button>
                        <button
                            className="rounded border-[#0c2d57] border bg-white hover:bg-[#dd3842] hover:text-white hover:border-white py-[15px] px-[34px] font-semibold leading-[20px] text-[#0c2d57] w-fit"
                            onClick={(e) => {
                                e.preventDefault()
                                setShowModal(false)
                            }}
                        >
                            Cancelar
                        </button>
                    </div>
                </form>
            </dialog>
            <div className="overflow-y-scroll grid grid-cols-1 gap-[20px] md:grid-cols-2 xl:grid-cols-3 h-full">
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
