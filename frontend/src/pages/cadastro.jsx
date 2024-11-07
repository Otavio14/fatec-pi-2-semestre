import { useState } from "react"
import { NavLink } from "react-router-dom"
import { Input } from "../components/input"
import { api } from "../shared/api"
import { Step1 } from "../components/cadastro-steps/cadastro-step1"
import { Step2 } from "../components/cadastro-steps/cadastro-step2"
import { useForm } from "../hooks/useForm"

export const CadastroPage = () => {

    const formData = {
        nome: "",
        email: "",
        senha: "",
        cep: "",
        estado: "",
        cidade: "",
        bairro: "",
        endereco: "",
        numero: "",
    }

    const [data, setData] = useState(formData)

    function dataChanger(name, value) {
         setData((prev) => {
            return { ...prev, [name]:value }
         })
    }

    const cadastro = (e) => {
        e.preventDefault()
        console.log("Sim")
        api.post("/clientes/", { ...userData }).then((res) => {
            console.log({ resposta: res }).catch((err) => {
                console.log({ erro: err })
            })
        })
    }

    const [step1, setStep1] = useState(true);
    const [step2, setStep2] = useState(false);

    const switchSteps = () => {
        setStep1(!step1)
        setStep2(!step2)
    }

    const formComponents = [<Step1 data={data} dataChanger={dataChanger}/>, <Step2 data={data} dataChanger={dataChanger}/>]
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
            <form onSubmit={(e) => changeStep(currentStep + 1, e)}
                className="relative flex w-full max-w-[500px] flex-col justify-center rounded-[10px] bg-white px-4 pb-[40px] pt-[33px] sm:px-[65px]"
            >
                <div>
                    {currentComponent}
                </div>
                <div className="flex flex row justify-center ">
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
                        type="button"
                    >
                        Enviar
                    </button>
                )}
                </div>
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