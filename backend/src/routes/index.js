import { Router } from "express";
import usuarioRoute from "./usuario.route.js";
import produtoRoute from "./produtos.route.js";
import taskRoute from "./task.route.js";

const router = Router();

router.use("/usuarios", usuarioRoute);
router.use("/produtos", produtoRoute);
router.use("/tasks", taskRoute);

export default router;
