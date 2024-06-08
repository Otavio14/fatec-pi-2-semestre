import { body, param } from "express-validator";

export const createAvaliacoesValidator = [
  body("produto").isInt().withMessage("Produto inválido"),
  body("cliente").isInt().withMessage("Cliente inválido"),
  body("nota").isInt().withMessage("Nota inválida"),
  body("comentario").isString().withMessage("Comentário inválido"),
  body("dtAvaliacao").isString().withMessage("Data inválida"),
];

export const updateAvaliacoesValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body("produto").isInt().withMessage("Produto inválido"),
  body("cliente").isInt().withMessage("Cliente inválido"),
  body("nota").isInt().withMessage("Nota inválida"),
  body("comentario").isString().withMessage("Comentário inválido"),
  body("dtAvaliacao").isString().withMessage("Data inválida"),
];

export const deleteAvaliacoesValidator = [
  param("id").isInt().withMessage("ID inválido"),
];
