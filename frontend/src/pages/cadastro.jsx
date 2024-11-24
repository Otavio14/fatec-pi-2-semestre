import { useState } from "react"
import { createPath, NavLink, useNavigate } from "react-router-dom"
import { Input } from "../components/input"
import { api } from "../shared/api"
import { Step1 } from "../components/cadastro-steps/cadastro-step1"
import { Step2 } from "../components/cadastro-steps/cadastro-step2"
import { useForm } from "../hooks/useForm"
import { isAdmin, isAuthenticated } from "../shared/auth"
import Swal from "sweetalert2"

export const CadastroPage = () => {

    const Navigate = useNavigate()

    const CadastroTemplate = {
        nome: "",
        email: "",
        telefone: "",
        senha: "",
        cep: "",
        idCidade: "",
        bairro: "",
        endereco: "",
        numero: "",
    }

    const [data, setData] = useState(CadastroTemplate)

    function updateFieldData(key, value) {
        setData((prev) => {
            return { ...prev, [key]: value }
        })
        console.log({ newUser: data })
    }

    const cadastro = (e) => {
        e.preventDefault()
        api.post("/clientes", { ...data }).then((res) => {
            Swal.fire({
                icon: "success",
                title: "Sucesso!",
                text: "Conta criada!",
            })
            api.post(`/usuarios/login`, { email: data.email, senha: data.senha }).then((res) => {
                localStorage.setItem("token", res.data)
                isAuthenticated() ? isAdmin() ? Navigate("/adm") : Navigate("/") : null
            }).catch((err) => console.log("Segundo catch: ", err))
        }).catch((err) => {
            console.log({ erro: err })
        })
    }

    const formComponents = [<Step1 data={data} updateFieldData={updateFieldData} />, <Step2 data={data} updateFieldData={updateFieldData} />]
    const { currentStep, currentComponent, changeStep, isLastStep, isFirstStep } = useForm(formComponents)

    return (
        <div className="flex h-full min-h-screen w-full flex-col items-center justify-center bg-gradient-to-r from-[#0c2d57] via-[#0c2d57b3] to-[#1a3a65] p-4">
            <NavLink
                to="/"
                className="absolute left-4 top-4 flex items-center justify-center rounded-full bg-white p-2 shadow-lg transition-all duration-300 hover:bg-[#dd3842] hover:text-white hover:-translate-x-1"
            >
                <span className="font-medium">Voltar</span>
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 24 24"
                    className="h-6 w-6"
                >
                    <path
                        d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z"
                    />
                </svg>
            </NavLink>

            <h2 className="mb-6 text-center text-[28px] font-bold text-white sm:text-[32px]">
                Cadastro
            </h2>
            <form
                onSubmit={isLastStep ? (e) => cadastro(e) : (e) => changeStep(currentStep + 1, e)}
                className="relative flex w-full max-w-[400px] flex-col rounded-xl bg-white p-6 shadow-lg sm:px-10"
            >
                <div className="mb-6">
                    {currentComponent}
                </div>
                <div className="flex justify-between">
                    {!isFirstStep && (
                        <button
                            style={{ transition: "all 0.3s ease-in-out" }}
                            className="rounded-lg bg-[#8f9eb2] px-6 py-3 text-white shadow-md hover:bg-[#7a8aa3] transition-all"
                            type="button"
                            onClick={() => changeStep(currentStep - 1)}
                        >
                            Voltar
                        </button>
                    )}
                    <button
                        style={{ transition: "all 0.3s ease-in-out" }}
                        className={`rounded-lg px-6 py-3 font-semibold text-white shadow-md transition-all ${isLastStep
                            ? "bg-[#32a852] hover:bg-[#2b9045]"
                            : "bg-[#dd3842] hover:bg-[#c32f39]"
                            }`}
                        type="submit"
                    >
                        {isLastStep ? "Enviar" : "Avançar"}
                    </button>
                </div>
                <div className="mt-4 text-center text-sm text-[#8f9eb2]">
                    Já possui uma conta?{" "}
                    <NavLink
                        to="/login"
                        className="text-[#dd3842] hover:underline hover:text-[#c32f39] transition-colors"
                    >
                        Entre
                    </NavLink>
                </div>
            </form>

        </div>

    )
}