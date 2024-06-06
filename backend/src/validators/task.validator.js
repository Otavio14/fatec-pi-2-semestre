import { body, param } from "express-validator";

export const createTaskValidator = [
  body("title").isString().withMessage("Título inválido"),
  body("userId").isInt().withMessage("ID do usuário inválido"),
];

export const updateTaskValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body("tittle").isString().withMessage("Título inválido"),
  body("UserId").isString().withMessage("ID do usuário inválido"),
];

export const deleteTaskValidator = [
  param("id").isInt().withMessage("ID inválido"),
];
