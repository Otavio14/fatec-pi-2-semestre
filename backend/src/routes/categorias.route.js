import { Router } from "express";
import CategoriasController from "../controllers/categorias.controller.js";
import {
  createCategoriasValidator,
  updateCategoriasValidator,
  deleteCategoriasValidator,
} from "../validators/categorias.validator.js";

const router = Router();

router.get("/", CategoriasController.index);
router.get("/:id", CategoriasController.show);
router.post("/", createCategoriasValidator, CategoriasController.create);
router.put("/:id", updateCategoriasValidator, CidadesCategorias.update);
router.delete("/:id", deleteCategoriasValidator, CidadesCategorias.delete);

export default router;
