import { Router } from "express";
import Produtos_CategoriasController from "../controllers/produtos_categorias.controller.js";
import {
  createProdutos_CategoriasValidator,
  updateProdutos_CategoriasValidator,
  deleteProdutos_CategoriasValidator,
} from "../validators/produtos_categorias.validator.js";

const router = Router();

router.get("/", Produtos_CategoriasController.index);
router.get("/:id", Produtos_CategoriasController.show);
router.post("/", createProdutos_CategoriasValidator, Produtos_CategoriasController.create);
router.put("/:id", updateProdutos_CategoriasValidator, Produtos_CategoriasController.update);
router.delete("/:id", deleteProdutos_CategoriasValidator, Produtos_CategoriasController.delete);
router.post("/login", createProdutos_CategoriasValidator, Produtos_CategoriasController.login);

export default router;
