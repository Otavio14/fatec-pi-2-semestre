import { body, param } from "express-validator";

export const createCategoriasValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCategoria"
          }
        }
      }
    }
  */
  body('nome').isString().withMessage("Nome inválido")
];

export const updateCategoriasValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCategoria"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido")
];

export const deleteCategoriasValidator = [
  param("id").isInt().withMessage("Id inválido")
];
