import { useEffect, useState } from "react";
import { Card } from "../components/card.jsx";
import { api } from "../shared/api.js";

export const HomePage = () => {
  const [Produtos, setProdutos] = useState([]);

  useEffect(() => {
    api.get("/produtos").then((res) => {
      setProdutos(res.data);
    });
  }, []);

  // const [Produtos, setProdutos] = useState([
  //   {
  //     id: 1,
  //     Text: "Whey Protein",
  //     Price: 83.99,
  //     Nota: 5,
  //     Image:
  //       "https://static.netshoes.com.br/produtos/nutri-whey-protein-900-g-pote-integralmedica/99/252-0951-799/252-0951-799_zoom1.jpg?ts=1695093963&ims=544x",
  //   },
  //   {
  //     id: 2,
  //     Text: "Whey Protein Gold Standard",
  //     Price: 279.9,
  //     Nota: 4,
  //     Image:
  //       "https://m.media-amazon.com/images/I/61GDn0-MvwL._AC_UF1000,1000_QL80_.jpg",
  //   },
  //   {
  //     id: 3,
  //     Text: "BCAA 2400 Max Titanium",
  //     Price: 59.9,
  //     Nota: 5,
  //     Image:
  //       "https://lojamaxtitanium.vtexassets.com/arquivos/ids/157363/bcaa-2400-max-titanium-60-capsulas-1.jpg?v=638343757987300000",
  //   },
  //   {
  //     id: 4,
  //     Text: "Creatina Hardcore Reload",
  //     Price: 29.9,
  //     Nota: 5,
  //     Image:
  //       "https://static.netshoes.com.br/produtos/creatina-monohidratada-hardcore-reload-300-gr-integralmedica/01/252-0831-001/252-0831-001_zoom1.jpg?ts=1695418005",
  //   },
  //   {
  //     id: 5,
  //     Text: "Glutamina Science",
  //     Price: 69.9,
  //     Nota: 5,
  //     Image:
  //       "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkGB69Hu6svYPz4ymqJ3Eez1FmswtyPXsEWw&s",
  //   },
  //   {
  //     id: 6,
  //     Text: "Hipercalórico Serious Mass",
  //     Price: 189.9,
  //     Nota: 4,
  //     Image:
  //       "https://a-static.mlcdn.com.br/450x450/serious-mass-morango-2720g-optimum-nutrition/megalandiltda/955ca87c2a3811ee8c2b4201ac18502e/23316fbaa73acace58a701f7cf2def24.jpeg",
  //   },
  //   {
  //     id: 7,
  //     Text: "Multivitamínico Daily Formula",
  //     Price: 49.9,
  //     Nota: 5,
  //     Image:
  //       "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEyH0tdx0WtalJy3Qd0mfzWjw2TS6VBrKaLw&s",
  //   },
  //   {
  //     id: 8,
  //     Text: "ZMA Optimum Nutrition",
  //     Price: 79.9,
  //     Nota: 5,
  //     Image:
  //       "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7SVSJUZEGrOViP6TUPgtHakjM2e3YCCG2_g&s",
  //   },
  //   {
  //     id: 9,
  //     Text: "Omega 3 Fish Oil",
  //     Price: 39.9,
  //     Nota: 3,
  //     Image:
  //       "https://i5.walmartimages.com/seo/Spring-Valley-Proactive-Support-Omega-3-from-Fish-Oil-Heart-General-Brain-Health-Dietary-Supplement-Softgels-1000-mg-120-Count_6518ee08-2b37-471b-aaa1-826e2bb74d68.5ef39bb1ae1fcb1bb3f2119e1202cdc7.jpeg",
  //   },
  // ]);

  return (
    <div className="flex flex-col items-center p-0 sm:p-20">
      <h1 className="mb-[33px] w-full border-b border-[#d9d9d9] pb-[12px] text-[38px] font-semibold leading-[140%]">
        Produtos
      </h1>
      <div className="grid grid-cols-1 gap-[20px] md:grid-cols-2 xl:grid-cols-3">
        {Produtos.map((produto, i) => (
          <Card
            key={i}
            Text={produto.nome}
            Price={produto.preco}
            Image={produto.imagem}
            Id={produto.id}
            // Nota={produto.Nota} Para evitar confusão por enquanto
          />
        ))}
      </div>
    </div>
  );
};
