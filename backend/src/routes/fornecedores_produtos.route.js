import { Router } from "express";
import Fornecedores_ProdutosController from "../controllers/fornecedores_produtos.controller.js";
import {
  createFornecedoresProdutosValidator,
  updateFornecedoresProdutosValidator,
  deleteFornecedoresProdutosValidator,
} from "../validators/fornecedores_produtos.validator.js";

const router = Router();

router.get("/", Fornecedores_ProdutosController.index);
router.get("/:id", Fornecedores_ProdutosController.show);
router.post(
  "/",
  createFornecedoresProdutosValidator,
  Fornecedores_ProdutosController.create
);
router.put(
  "/:id",
  updateFornecedoresProdutosValidator,
  Fornecedores_ProdutosController.update
);
router.delete(
  "/:id",
  deleteFornecedoresProdutosValidator,
  Fornecedores_ProdutosController.delete
);

export default router;
