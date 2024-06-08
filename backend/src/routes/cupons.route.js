import { Router } from "express";
import CuponsController from "../controllers/cupons.controller.js";
import {
  createCuponsValidator,
  updateCuponsValidator,
  deleteCuponsValidator,
} from "../validators/cupons.validator.js";

const router = Router();

router.get("/", CuponsController.index);
router.get("/:id", CuponsController.show);
router.post("/", createCuponsValidator, CuponsController.create);
router.put("/:id", updateCuponsValidator, CuponsController.update);
router.delete("/:id", deleteCuponsValidator, CuponsController.delete);

export default router;
