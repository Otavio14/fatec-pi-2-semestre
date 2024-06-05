import { body, param } from "express-validator";

export const createCidadesValidator = [
  body('nome').isString().withMessage("Nome inválido"),
  body('estado').isInt().withMessage("Estado inválido")
];

export const updateCidadesValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('estado').isInt().withMessage("Estado inválido")
];

export const deleteCidadesValidator = [
  param("id").isInt().withMessage("Id inválido")
];
