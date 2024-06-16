import { body, param } from "express-validator";

export const createCuponsValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCupom"
          }
        }
      }
    }
  */
  body('nome').isString().withMessage("Nome inválido"),
  body('porcentagem').isNumeric().withMessage("Porcentagem inválida")
];

export const updateCuponsValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCupom"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('porcentagem').isNumeric().withMessage("Porcentagem inválida")
];

export const deleteCuponsValidator = [
  param("id").isInt().withMessage("Id inválido")
];
