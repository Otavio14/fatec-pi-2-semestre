import { body, param } from "express-validator";

export const createClientesValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCliente"
          }
        }
      }
    }
  */
  body("id_cidades").isInt().withMessage("Cidade inválida"),
  body("cep").isString().withMessage("CEP inválido"),
  body("email").isString().withMessage("E-mail inválido"),
  body("telefone").isString().withMessage("Telefone inválido"),
  body("bairro").isString().withMessage("Bairro inválido"),
  body("numero").isInt().withMessage("Numero inválido"),
  body("endereco").isString().withMessage("Endereço inválido"),
  body("nome").isString().withMessage("Nome inválido"),
];

export const updateClientesValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateCliente"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body("id_cidades").isInt().withMessage("Cidade inválido"),
  body("cep").isString().withMessage("CEP inválido"),
  body("email").isString().withMessage("E-mail inválido"),
  body("telefone").isString().withMessage("Telefone inválido"),
  body("bairro").isString().withMessage("Bairro inválido"),
  body("numero").isInt().withMessage("Numero inválido"),
  body("endereco").isString().withMessage("Endereço inválido"),
  body("nome").isString().withMessage("Nome inválido"),
];

export const deleteClientesValidator = [
  param("id").isInt().withMessage("ID inválido"),
];
