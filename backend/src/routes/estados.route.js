import { Router } from "express";
import EstadosController from "../controllers/estados.controller.js";
import {
  createEstadoValidator,
  updateEstadoValidator,
  deleteEstadoValidator,
} from "../validators/estados.validator.js";

const router = Router();

router.get("/", EstadosController.index);
router.get("/:id", EstadosController.show);
router.post("/", createEstadoValidator, EstadosController.create);
router.put("/:id", updateEstadoValidator, EstadosController.update);
router.delete("/:id", deleteEstadoValidator, EstadosController.delete);

export default router;
