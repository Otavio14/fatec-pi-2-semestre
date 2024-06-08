import { Router } from "express";
import FornecedoresController from "../controllers/fornecedores.controller.js";
import {
  createFornecedoresValidator,
  updateFornecedoresValidator,
  deleteFornecedoresValidator,
} from "../validators/fornecedores.validator.js";

const router = Router();

router.get("/", FornecedoresController.index);
router.get("/:id", FornecedoresController.show);
router.post("/", createFornecedoresValidator, FornecedoresController.create);
router.put("/:id", updateFornecedoresValidator, FornecedoresController.update);
router.delete("/:id", deleteFornecedoresValidator, FornecedoresController.delete);

export default router;
