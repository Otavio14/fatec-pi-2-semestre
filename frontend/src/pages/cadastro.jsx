import { useState } from "react"
import { NavLink } from "react-router-dom"
import { Input } from "../components/input"
import { api } from "../shared/api"

export const CadastroPage = () => {

    const [nome, setNome] = useState("")
    const [email, setEmail] = useState("")
    const [senha, setSenha] = useState("")

    const userData = {
        nome: nome,
        email: email,
        senha: senha
    }

    const cadastro = (e) => {
        e.preventDefault()
        console.log("Sim")
        api.post("/usuarios/", { ...userData }).then((res) => {
            console.log({ resposta: res }).catch((err) => {
                console.log({ erro: err })
            })
        })
    }

    return (
        <div className="flex h-full min-h-screen w-full min-w-full flex-col items-center justify-center bg-[#0c2d57b3] p-1">
            <form
                onSubmit={cadastro}
                className="relative flex w-full max-w-[500px] flex-col justify-center rounded-[10px] bg-white px-4 pb-[40px] pt-[33px] sm:px-[65px]"
            >
                <NavLink
                    to="/"
                    className="absolute right-[20px] top-[15px] text-[#8f9eb2]"
                >
                    Voltar para a Home
                </NavLink>
                <h2 className="mb-[20px] text-[30px] font-semibold leading-[140%] text-[#0c2d57]">
                    Cadastro
                </h2>
                <Input
                    Label={"Nome Completo"}
                    value={nome}
                    placeholder={"Digite seu nome e sobrenome"}
                    onChange={(event) => setNome(event.target.value)}
                    required
                    type="text"
                    autoComplete="text"
                />
                <Input
                    Label={"Email"}
                    value={email}
                    placeholder={"Digite seu email"}
                    onChange={(event) => setEmail(event.target.value)}
                    required
                    type="email"
                    autoComplete="email"
                />
                <Input
                    Label={"Senha"}
                    value={senha}
                    placeholder={"*********"}
                    onChange={(event) => setSenha(event.target.value)}
                    required
                    type="password"
                    autoComplete="password"
                />
                <button
                    style={{ transition: "color .3s, background-color .5s" }}
                    className="mt-[14px] rounded border bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[22px] text-white hover:bg-white hover:text-[#0c2d57]"
                >
                    Entrar
                </button>
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