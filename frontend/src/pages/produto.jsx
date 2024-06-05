import { useEffect, useState } from "react";
import Star from "../assets/star.png";
import { api } from "../shared/api";
import { useParams } from "react-router-dom";

export const ProdutoPage = () => {
  // const [Produto, setProduto] = useState({
  //   nota: 5,
  //   nome: "Whey Protein",
  //   preco: 83.3,
  //   descricao: `O whey protein é um suplemento fabricado a partir do soro do leite (em inglês, “whey”), um subproduto resultante da fabricação de queijos por coagulação da caseína. Possui alto valor nutricional devido à presença de proteínas com elevado teor de aminoácidos essenciais. Essas  são obtidas por meio de processos industriais de pasteurização, filtração e microfiltração do soro. A quantidade de filtrações e o tamanho dos filtros utilizados estabelecem o tipo e a qualidade do produto obtido -- isolado, concentrado ou hidrolisado. A apresentação final do whey protein é a de um pó parecido com o leite em pó. Saiba mais sobre o que é whey protein, para que serve e benefícios`,
  // });
  let Id = useParams()
  const ProdutoId = Id.id

  const [Produto, setProduto] = useState([]);
  const [Quantidade, setQuantidade] = useState(1);

  useEffect(() => {
    api.get(`/produtos/${parseInt(ProdutoId)}`).then((res) => {
      setProduto(res.data)
    })
  }, []);

  return (
    <div className="flex flex-col items-center p-20 bg-[#f8f9ff]">
      <div className="flex gap-8">
        <div className="bg-white flex justify-center items-center border border-[#e7eaee] rounded w-full h-[600px] py-[60px] px-[50px] max-w-[600px]">
          <img
            className="object-contain max-w-[450px] max-h-[450px]"
            src={Produto.imagem}
          />
        </div>
        <div className="flex flex-col items-stretch max-w-[480px] mt-[16px] w-[581px]">
          <h1 className="mb-[12px] text-[30px] font-semibold leading-[40px]">
            {Produto.nome}
          </h1>
          <div className="flex items-center mb-1 gap-1">
            {Array(Produto.nota || 0)
              ?.fill(null)
              ?.map((_, index) => (
                <img key={index} src={Star} className="w-[18px] h-[18px]" />
              ))}
          </div>
          <p className="text-[30px] font-semibold leading-[40px] text-[#dd3842] border-b border-b-[#8f9eb2] mt-[20px] mb-[22px] pb-[16px] w-full">
            R$ {String(Produto.preco?.toFixed(2))?.replace(".", ",")}
          </p>
          <p className="text-[#5c728e] leading-[28px] text-justify">
            {Produto.descricao}
          </p>
          <div className="flex gap-[10px] mt-[23px] mb-[28px]">
            <input
              type="number"
              value={Quantidade}
              onChange={(e) => setQuantidade(e.target.value)}
              className="border border-[#5c728e] rounded w-full h-[50px] max-w-[120px] pl-[50px] text-[18px] font-medium leading-[28px] bg-transparent"
            />

            <button
              style={{ transition: "color .2s, background-color .4s" }}
              className="border border-[#0c2d57] bg-white rounded text-[#0c2d57] w-full py-[14px] px-[15px] hover:border-[#dd3842] hover:bg-[#dd3842] hover:text-white"
            >
              Adicionar ao Carrinho
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
