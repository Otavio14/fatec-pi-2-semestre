import { body, param } from "express-validator";

export const createCategoriasValidator = [
  body('nome').isString().withMessage("Nome inválido")
];

export const updateCategoriasValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido")
];

export const deleteCategoriasValidator = [
  param("id").isInt().withMessage("Id inválido")
];
