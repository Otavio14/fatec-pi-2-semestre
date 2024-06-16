import { body, param } from "express-validator";

export const createAvaliacoesValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateAvaliacao"
          }
        }
      }
    }
  */
  body("produto").isInt().withMessage("Produto inválido"),
  body("cliente").isInt().withMessage("Cliente inválido"),
  body("nota").isInt().withMessage("Nota inválida"),
  body("comentario").isString().withMessage("Comentário inválido"),
  body("dtAvaliacao").isString().withMessage("Data inválida"),
];

export const updateAvaliacoesValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateAvaliacao"
          }
        }
      }
    }
  */
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
