import { Navigate, createBrowserRouter } from "react-router-dom";
import { HomePage } from "./pages/home.jsx";
import { LoginPage } from "./pages/login.jsx";
import { HeaderComponent } from "./components/header.jsx";
import { ContatoPage } from "./pages/contato.jsx";
import { ProdutoPage } from "./pages/produto.jsx";
import { AdminHomePage } from "./pages/admin-home.jsx";
import { AdminHeader } from "./components/admin-header.jsx";
import { UsuarioPage } from "./pages/usuario.jsx";
import { ProdutosPage } from "./pages/produtosAdmPage.jsx";
import { AdminRoute } from "./shared/auth.jsx";
import { FinalizarCompraPage } from "./pages/finalizar-compra.jsx";

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
        <>
          <HeaderComponent />
          <FinalizarCompraPage />
        </>
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
      ],
    },
  ],
  { basename: "/fatec-pi-2-semestre" },
);
