import { body, param } from "express-validator";

export const createCategoraiasValidator = [
  body('nome').isString().withMessage("Nome inválido")
];

export const updateCategoraiasValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido")
];

export const deleteCategoraiasValidator = [
  param("id").isInt().withMessage("Id inválido")
];
