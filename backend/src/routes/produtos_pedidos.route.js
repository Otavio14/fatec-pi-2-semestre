import { Router } from "express";
import Produtos_PedidosController from "../controllers/produtos_pedidos.controller.js";
import {
  createProdutos_PedidosValidator,
  updateProdutos_PedidosValidator,
  deleteProdutos_PedidosValidator,
} from "../validators/produtos_pedidos.validator.js";

const router = Router();

router.get("/", Produtos_PedidosController.index);
router.get("/:id", Produtos_PedidosController.show);
router.post("/", createProdutos_Pedidosalidator, Produtos_PedidosController.create);
router.put("/:id", updateProdutos_PedidosValidator, Produtos_PedidosController.update);
router.delete("/:id", deleteProdutos_PedidosValidator, Produtos_PedidosController.delete);

export default router;
