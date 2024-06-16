import { body, param } from "express-validator";

export const createEstadoValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateEstado"
          }
        }
      }
    }
  */
  body('nome').isString().withMessage("Nome inválido"),
  body('sigla').isString().withMessage("Sigla inválida")
];

export const updateEstadoValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateEstado"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('sigla').isString().withMessage("Sigla inválida")
];

export const deleteEstadoValidator = [
  param("id").isInt().withMessage("Id inválido")
];
