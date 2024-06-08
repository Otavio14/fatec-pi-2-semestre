import { Router } from "express";
import usuarioRoute from "./usuario.route.js";
import produtoRoute from "./produtos.route.js";
import cidadeRoute from "./cidades.route.js";
import estadoRoute from "./estados.route.js";
import avaliacaoRoute from "./avaliacoes.route.js";
import categoriaRoute from "./categorias.route.js";
import clienteRoute from "./clientes.route.js";
import cupomRoute from "./cupons.route.js";
import fornecedorRoute from "./fornecedores.route.js";
import fornecedorProdutoRoute from "./fornecedores_produtos.route.js";
import pedidoRoute from "./pedidos.route.js";
import produtoCategoriaRoute from "./produtos_categorias.route.js";
import produtoPedidoRoute from "./produtos_pedidos.route.js";

const router = Router();

router.use("/avaliacoes", avaliacaoRoute);
router.use("/categorias", categoriaRoute);
router.use("/cidades", cidadeRoute);
router.use("/clientes", clienteRoute);
router.use("/cupons", cupomRoute);
router.use("/estados", estadoRoute);
router.use("/fornecedores_produtos", fornecedorProdutoRoute);
router.use("/fornecedores", fornecedorRoute);
router.use("/pedidos", pedidoRoute);
router.use("/produtos-categorias", produtoCategoriaRoute);
router.use("/produtos-pedidos", produtoPedidoRoute);
router.use("/produtos", produtoRoute);
router.use("/usuarios", usuarioRoute);

export default router;
