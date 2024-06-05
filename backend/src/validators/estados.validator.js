import { body, param } from "express-validator";

export const createEstadoValidator = [
  body('nome').isString().withMessage("Nome inválido"),
  body('sigla').isString().withMessage("Sigla inválida")
];

export const updateEstadoValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('sigla').isString().withMessage("Sigla inválida")
];

export const deleteEstadoValidator = [
  param("id").isInt().withMessage("Id inválido")
];
