import { Navigate, createBrowserRouter } from "react-router-dom";
import { AdminHeader } from "./components/admin-header.jsx";
import { HeaderComponent } from "./components/header.jsx";
import { AdminHomePage } from "./pages/admin-home.jsx";
import { AvaliacaoPage } from "./pages/avaliacao.jsx";
import { CadastroPage } from "./pages/cadastro.jsx";
import { CategoriaPage } from "./pages/categoria.jsx";
import { ClientePage } from "./pages/cliente.jsx";
import { ContatoPage } from "./pages/contato.jsx";
import { CupomPage } from "./pages/cupom.jsx";
import { FinalizarCompraPage } from "./pages/finalizar-compra.jsx";
import { FornecedorProdutoPage } from "./pages/fornecedor-produto.jsx";
import { FornecedorPage } from "./pages/fornecedor.jsx";
import { HomePage } from "./pages/home.jsx";
import { LoginPage } from "./pages/login.jsx";
import { MeuPedido } from "./pages/meu-pedido.jsx";
import { PedidoPage } from "./pages/pedido.jsx";
import { ProdutoCategoriaPage } from "./pages/produto-categoria.jsx";
import { ProdutoPage } from "./pages/produto.jsx";
import { ProdutosPage } from "./pages/produtosAdmPage.jsx";
import { ProfilePage } from "./pages/profile.jsx";
import { UsuarioPage } from "./pages/usuario.jsx";
import { AdminRoute, ClienteRoute } from "./shared/auth-routes.jsx";

export const routes = createBrowserRouter(
  [
    {
      path: "*",
      element: <Navigate to="/" replace={true} />,
    },
    {
      path: "/",
      element: (
        <>
          <HeaderComponent />
          <HomePage />
        </>
      ),
    },
    {
      path: "/login",
      element: <LoginPage />,
    },
    {
      path: "/cadastro",
      element: <CadastroPage />,
    },
    {
      path: "/contato",
      element: (
        <>
          <HeaderComponent />
          <ContatoPage />
        </>
      ),
    },
    {
      path: "/produto/:id",
      element: (
        <>
          <HeaderComponent />
          <ProdutoPage />
        </>
      ),
    },
    {
      path: "/finalizar-compra",
      element: (
        <ClienteRoute>
          <HeaderComponent />
          <FinalizarCompraPage />
        </ClienteRoute>
      ),
    },
    {
      path: "/perfil",
      element: (
        <ClienteRoute>
          <HeaderComponent />
          <ProfilePage />
        </ClienteRoute>
      ),
    },
    {
      path: "/meu-pedido",
      element: (
        <ClienteRoute>
          <HeaderComponent />
          <MeuPedido />
        </ClienteRoute>
      ),
    },
    {
      path: "/admin",
      element: (
        <AdminRoute>
          <AdminHeader />
        </AdminRoute>
      ),
      children: [
        { path: "", element: <AdminHomePage /> },
        {
          path: "usuarios",
          element: <UsuarioPage />,
        },
        {
          path: "produtos",
          element: <ProdutosPage />,
        },
        {
          path: "avaliacoes",
          element: <AvaliacaoPage />,
        },
        {
          path: "fornecedores",
          element: <FornecedorPage />,
        },
        {
          path: "pedidos",
          element: <PedidoPage />,
        },
        {
          path: "cupons",
          element: <CupomPage />,
        },
        {
          path: "categorias",
          element: <CategoriaPage />,
        },
        {
          path: "clientes",
          element: <ClientePage />,
        },
        {
          path: "fornecedores-produtos",
          element: <FornecedorProdutoPage />,
        },
        {
          path: "produtos-categorias",
          element: <ProdutoCategoriaPage />,
        },
      ],
    },
  ],
  { basename: "/fatec-pi-2-semestre" },
);
