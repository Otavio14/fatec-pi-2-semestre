import { useState } from "react"
import { createPath, NavLink, useNavigate } from "react-router-dom"
import { Input } from "../components/input"
import { api } from "../shared/api"
import { Step1 } from "../components/cadastro-steps/cadastro-step1"
import { Step2 } from "../components/cadastro-steps/cadastro-step2"
import { useForm } from "../hooks/useForm"

export const CadastroPage = () => {

    const Navigate = useNavigate()

    const CadastroTemplate = {
        nome: "",
        email: "",
        telefone: "",
        senha: "",
        cep: "",
        estado: "",
        cidade: "",
        bairro: "",
        endereco: "",
        numero: "",
    }

    const [data, setData] = useState(CadastroTemplate)

    function updateFieldData(key, value) {
        setData((prev) => {
            return { ...prev, [key]: value }
        })
    }

    const cadastro = (e) => {
        e.preventDefault()
        api.post("/clientes/", { ...data }).then((res) => {
            console.log({ resposta: res })
            alert(" Conta criada! ")
            localStorage.setItem("tokenTeste", data.email)
            Navigate("/")
        }).catch((err) => {
            console.log({ erro: err })
        })
    }

    const formComponents = [<Step1 data={data} updateFieldData={updateFieldData} />, <Step2 data={data} updateFieldData={updateFieldData} />]
    const { currentStep, currentComponent, changeStep, isLastStep, isFirstStep } = useForm(formComponents)

    return (
        <div className="flex h-full min-h-screen w-full min-w-full flex-col items-center justify-center bg-[#0c2d57b3] p-1">
            <NavLink
                to="/"
                className="right-[20px] top-[15px] text-[#8f9eb2]"
            >
                Voltar para a Home
            </NavLink>
            <h2 className="mb-[20px] text-[30px] font-semibold leading-[140%] text-[#0c2d57]">
                Cadastro
            </h2>
            <form onSubmit={isLastStep ? (e) => cadastro(e) : (e) => changeStep(currentStep + 1, e)}
                className="relative flex w-full max-w-[500px] flex-col justify-center rounded-[10px] bg-white px-4 pb-[40px] pt-[33px] sm:px-[65px]"
            >
                <div>
                    {currentComponent}
                </div>
                {!isFirstStep && (
                    <button
                        style={{ transition: "color .3s, background-color .5s" }}
                        className="mt-[14px] rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[22px] text-white hover:bg-white hover:text-[#0c2d57]"
                        type="button"
                        onClick={() => changeStep(currentStep - 1)}
                    >
                        Voltar
                    </button>
                )}
                {!isLastStep ? (
                    <button style={{ transition: "color .3s, background-color .5s" }}
                        className="mt-[14px] rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[22px] text-white hover:bg-white hover:text-[#0c2d57]"
                        type="submit"
                    >
                        <span>Avançar</span>
                    </button>
                ) : (
                    <button style={{ transition: "color .3s, background-color .5s" }}
                        className="mt-[14px] rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[22px] text-white hover:bg-white hover:text-[#0c2d57]"
                        type="submit"
                    >
                        Enviar
                    </button>
                )}
            </form>
            <NavLink
                to="/login"
                className="absolute right-[20px] top-[15px] text-[#8f9eb2]"
            >
                Fazer Log in
            </NavLink>
        </div>
    )
}