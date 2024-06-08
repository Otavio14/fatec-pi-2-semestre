import { Router } from "express";
import EstadosController from "../controllers/estados.controller.js";
import {
  createEstadosValidator,
  updateEstadosValidator,
  deleteEstadosValidator,
} from "../validators/estados.validator.js";

const router = Router();

router.get("/", EstadosController.index);
router.get("/:id", EstadosController.show);
router.post("/", createEstadosValidator, EstadosController.create);
router.put("/:id", updateEstadosValidator, EstadosController.update);
router.delete("/:id", deleteEstadosValidator, EstadosController.delete);

export default router;
