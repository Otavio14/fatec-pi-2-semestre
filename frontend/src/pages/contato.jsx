import { Input } from "../components/input";
import { Swal } from "../shared/swal";

export const ContatoPage = () => {
  const enviar = (event) => {
    event.preventDefault();

    Swal.fire({
      title: "Em breve!",
      text: "Esta é apenas uma prévia!",
      icon: "info",
    });
  };

  return (
    <div
      style={{ minHeight: "calc(100vh - 123px)" }}
      className="flex h-full flex-col items-center justify-center p-2 sm:p-8"
    >
      <div className="flex flex-col items-center gap-8 md:flex-row">
        <div className="flex flex-col gap-5">
          <h1 className="text-[48px] font-semibold leading-[140%]">
            Como podemos ajudar?
          </h1>
          <p className="mb-[47px] max-w-[750px] text-[#5c728e]">
            Entre em contato conosco através do formulário ao lado em caso de
            alguma pergunta, crítica ou comentário.
          </p>
        </div>
        <form
          onSubmit={enviar}
          className="flex w-full max-w-[650px] flex-col items-stretch gap-5 rounded-[10px] border border-[#e8e8e8] bg-white p-[30px]"
        >
          <h2 className="text-[30px] font-semibold leading-[40px]">
            Entre em contato
          </h2>
          <p className="max-w-[348px] leading-[25px] text-[#666]">
            Envie-nos a sua mensagem através do formulário.
          </p>
          <div className="grid grid-cols-1 gap-x-[30px] sm:grid-cols-2">
            <Input Label="Nome" placeholder="Nome Sobrenome" />
            <Input Label="Email" placeholder="nome@mail.com" />
            <Input Label="Celular" placeholder="(19) 91234-5678" />
            <Input Label="Assunto" placeholder="Seu tópico" />
            <span className="sm:col-span-2">
              <Input
                Label="Mensagem"
                placeholder="Comentários..."
                Textarea
                Rows={10}
                Cols={60}
              />
            </span>
          </div>
          <button
            style={{ transition: "color .3s, background-color .5s" }}
            className="w-fit self-center rounded bg-[#dd3842] px-[34px] py-[15px] font-semibold leading-[20px] text-white sm:self-start"
          >
            Enviar
          </button>
        </form>
      </div>
    </div>
  );
};
