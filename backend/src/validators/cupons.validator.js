import { body, param } from "express-validator";

export const createCuponsValidator = [
  body('nome').isString().withMessage("Nome inválido"),
  body('porcentagem').isNumeric().withMessage("Porcentagem inválida")
];

export const updateCuponsValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('porcentagem').isNumeric().withMessage("Porcentagem inválida")
];

export const deleteCuponsValidator = [
  param("id").isInt().withMessage("Id inválido")
];
