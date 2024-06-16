import { body, param } from "express-validator";

export const createUsuarioValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateUser"
          }
        }
      }
    }
  */
  body("email").isEmail().withMessage("Email inválido"),
  body("nome").isString().withMessage("Nome inválido"),
  body("senha").isInt().withMessage("Senha inválida"),
];

export const updateUsuarioValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateUser"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body("email").isEmail().withMessage("Email inválido"),
  body("nome").isString().withMessage("Nome inválido"),
  body("senha").isInt().withMessage("Senha inválida"),
];

export const deleteUsuarioValidator = [
  param("id").isInt().withMessage("Id inválido"),
];
