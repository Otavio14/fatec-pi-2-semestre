import { body, param } from "express-validator";

export const createUsuarioValidator = [
  body("email").isEmail().withMessage("Email inválido"),
  body("nome").isString().withMessage("Nome inválido"),
  body("senha").isInt().withMessage("Senha inválida"),
];

export const updateUsuarioValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body("email").isEmail().withMessage("Email inválido"),
  body("nome").isString().withMessage("Nome inválido"),
  body("senha").isInt().withMessage("Senha inválida"),
];

export const deleteUsuarioValidator = [
  param("id").isInt().withMessage("Id inválido"),
];
