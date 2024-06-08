import { Router } from "express";
import AvaliacoesController from "../controllers/avaliacoes.controller.js";
import {
  createAvaliacoesValidator,
  updateAvaliacoesValidator,
  deleteAvaliacoesValidator,
} from "../validators/avaliacoes.validator.js";

const router = Router();

router.get("/", AvaliacoesController.index);
router.get("/:id", AvaliacoesController.show);
router.post("/", createAvaliacoesValidator, AvaliacoesController.create);
router.put("/:id", updateAvaliacoesValidator, AvaliacoesController.update);
router.delete("/:id", deleteAvaliacoesValidator, AvaliacoesController.delete);

export default router;
