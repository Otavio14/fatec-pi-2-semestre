import { Router } from "express";
import usuarioRoute from "./usuario.route.js";
import produtoRoute from "./produtos.route.js";
import taskRoute from "./task.route.js";
import cidadeRoute from "./cidades.route.js";
import estadoRoute from "./estados.route.js";

const router = Router();

router.use("/usuarios", usuarioRoute);
router.use("/produtos", produtoRoute);
router.use("/cidades", cidadeRoute);
router.use("/estados", estadoRoute);
router.use("/tasks", taskRoute);

export default router;
