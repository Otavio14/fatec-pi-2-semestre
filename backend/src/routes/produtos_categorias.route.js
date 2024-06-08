import { Router } from "express";
import Produtos_CategoriasController from "../controllers/produtos_categorias.controller.js";
import {
  createProdutosCategoriasValidator,
  updateProdutosCategoriasValidator,
  deleteProdutosCategoriasValidator,
} from "../validators/produtos_categorias.validator.js";

const router = Router();

router.get("/", Produtos_CategoriasController.index);
router.get("/:id", Produtos_CategoriasController.show);
router.post(
  "/",
  createProdutosCategoriasValidator,
  Produtos_CategoriasController.create
);
router.put(
  "/:id",
  updateProdutosCategoriasValidator,
  Produtos_CategoriasController.update
);
router.delete(
  "/:id",
  deleteProdutosCategoriasValidator,
  Produtos_CategoriasController.delete
);

export default router;
