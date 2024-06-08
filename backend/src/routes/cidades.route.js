import { Router } from "express";
import CidadesController from "../controllers/cidades.controller.js";
import {
  createCidadesValidator,
  updateCidadesValidator,
  deleteCidadesValidator,
} from "../validators/cidades.validator.js";

const router = Router();

router.get("/", CidadesController.createAuto);
router.get("/:id", CidadesController.show);
router.post("/", createCidadesValidator, CidadesController.create);
router.put("/:id", updateCidadesValidator, CidadesController.update);
router.delete("/:id", deleteCidadesValidator, CidadesController.delete);

export default router;
