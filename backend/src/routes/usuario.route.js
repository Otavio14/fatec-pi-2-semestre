import { Router } from "express";
import UsuarioController from "../controllers/usuarios.controller.js";
import {
  createUsuarioValidator,
  updateUsuarioValidator,
  deleteUsuarioValidator,
} from "../validators/usuario.validator.js";

const router = Router();

router.get("/", UsuarioController.index);
router.get("/:id", UsuarioController.show);
router.post("/", createUsuarioValidator, UsuarioController.create);
router.put("/:id", updateUsuarioValidator, UsuarioController.update);
router.delete("/:id", deleteUsuarioValidator, UsuarioController.delete);

export default router;
