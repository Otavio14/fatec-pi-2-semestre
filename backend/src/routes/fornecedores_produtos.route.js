import { Router } from "express";
import Fornecedores_ProdutosController from "../controllers/fornecedores_produtos.controller.js";
import {
  createFornecedores_ProdutosValidator,
  updateFornecedores_ProdutosValidator,
  deleteFornecedores_ProdutosValidator,
} from "../validators/produtos.validator.js";

const router = Router();

router.get("/", Fornecedores_ProdutosController.index);
router.get("/:id", Fornecedores_ProdutosController.show);
router.post("/", createFornecedores_ProdutosValidator, Fornecedores_ProdutosController.create);
router.put("/:id", updateFornecedores_ProdutosValidator, Fornecedores_ProdutosController.update);
router.delete("/:id", deleteFornecedores_ProdutosValidator, Fornecedores_ProdutosController.delete);

export default router;
