import { useState } from "react";
import Swal from "sweetalert2";
import { EnderecoModal } from "../endereco-modal";
import { Input } from "../input";

export const ProfileInfo = ({
  show,
  setShow,
  userData,
  handleSalvar,
  setHasChanged,
  hasChanged,
  confirmChange,
}) => {
  const [showEndereco, setShowEndereco] = useState(false);

  return (
    <div className={`${show ? "flex justify-center" : "hidden"}`}>
      <div className="flex w-full flex-col">
        <div className="rounded-lg bg-gray-100 p-6 shadow-md">
          <Input
            Label={"nome"}
            value={userData.nome}
            onChange={(e) => handleSalvar("nome", e.target.value)}
          />
          <Input
            Label={"email"}
            value={userData.email}
            onChange={(e) => handleSalvar("email", e.target.value)}
          />
          <Input
            Label={"celular"}
            value={userData?.telefone}
            onChange={(e) =>
              handleSalvar(
                "telefone",
                e.target.value
                  .replace(/\D/g, "")
                  .replace(/(\d{2})(\d)/, "($1) $2")
                  .replace(/(\d{5})(\d)/, "$1-$2")
                  .slice(0, 14),
              )
            }
          />
        </div>
        <div className="mt-6 flex flex-wrap items-center gap-4">
          <button
            className="rounded-xl bg-blue-500 px-6 py-2 font-semibold text-white shadow-lg transition-all duration-200 hover:bg-blue-600"
            onClick={() => setShowEndereco(!showEndereco)}
          >
            Endereço
          </button>
          <button
            className={`rounded-xl px-6 py-2 font-semibold shadow-lg transition-all duration-200 ${hasChanged ? "bg-green-500 text-white hover:bg-green-600" : "cursor-not-allowed bg-gray-400 text-gray-200"}`}
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
                setHasChanged(false);
              }
            }}
          >
            Salvar
          </button>
        </div>
      </div>
      <EnderecoModal
        show={showEndereco}
        setShow={setShowEndereco}
        cep={userData.cep}
        bairro={userData.bairro}
        rua={userData.endereco}
        numero={userData.numero}
        idCidade={userData.idCidade}
        updateFieldData={handleSalvar}
        confirmChange={confirmChange}
        hasChanged={hasChanged}
        setHasChanged={setHasChanged}
      />
    </div>
  );
};
