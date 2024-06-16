import { body, param } from "express-validator";

export const createCidadesValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCidade"
          }
        }
      }
    }
  */
  body('nome').isString().withMessage("Nome inválido"),
  body('estado').isInt().withMessage("Estado inválido")
];

export const updateCidadesValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCidade"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('estado').isInt().withMessage("Estado inválido")
];

export const deleteCidadesValidator = [
  param("id").isInt().withMessage("Id inválido")
];
