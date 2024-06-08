import { Router } from "express";
import Produtos_PedidosController from "../controllers/produtos_pedidos.controller.js";
import {
  createProdutosPedidosValidator,
  updateProdutosPedidosValidator,
  deleteProdutosPedidosValidator,
} from "../validators/produtos_pedidos.validator.js";

const router = Router();

router.get("/", Produtos_PedidosController.index);
router.get("/:id", Produtos_PedidosController.show);
router.post(
  "/",
  createProdutosPedidosValidator,
  Produtos_PedidosController.create
);
router.put(
  "/:id",
  updateProdutosPedidosValidator,
  Produtos_PedidosController.update
);
router.delete(
  "/:id",
  deleteProdutosPedidosValidator,
  Produtos_PedidosController.delete
);

export default router;
