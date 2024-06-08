import { Router } from "express";
import PedidosController from "../controllers/pedidos.controller.js";
import {
  createPedidosValidator,
  updatePedidosValidator,
  deletePedidosValidator,
} from "../validators/pedidos.validator.js";

const router = Router();

router.get("/", PedidosController.index);
router.get("/:id", PedidosController.show);
router.post("/", createPedidosalidator, PedidosController.create);
router.put("/:id", updatePedidosValidator, PedidosController.update);
router.delete("/:id", deletePedidosValidator, PedidosController.delete);

export default router;
