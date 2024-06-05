import { body, param } from "express-validator";

export const createCuponValidator = [
  body('nome').isString().withMessage("Nome inválido"),
  body('porcentagem').isNumeric().withMessage("Porcentagem inválida")
];

export const updateCuponValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('porcentagem').isNumeric().withMessage("Porcentagem inválida")
];

export const deleteCuponValidator = [
  param("id").isInt().withMessage("Id inválido")
];
