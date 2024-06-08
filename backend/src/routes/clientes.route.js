import { Router } from "express";
import ClientesController from "../controllers/clientes.controller.js";
import {
  createClientesValidator,
  updateClientesValidator,
  deleteClientesValidator,
} from "../validators/clientes.validator.js";

const router = Router();

router.get("/", ClientesController.index);
router.get("/:id", ClientesController.show);
router.post("/", createClientesValidator, ClientesController.create);
router.put("/:id", updateClientesValidator, ClientesController.update);
router.delete("/:id", deleteClientesValidator, ClientesController.delete);

export default router;
