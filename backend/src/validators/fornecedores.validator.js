import { body, param } from 'express-validator';

export const createFornecedoresValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateFornecedor"
          }
        }
      }
    }
  */
  body('nome').isString().withMessage("Nome inválido"),
  body('endereco').isString().withMessage("Endereco inválido"),
  body('cep').isString().withMessage("CEP inválido"),
  body('complemento').isString().withMessage("Complemento inválido"),
  body('telefone').isString().withMessage("Telefone inválido"),
  body('status').isString().withMessage("Status inválido"),
  body('id_cidades').isInt().withMessage("Cidade inválida")
];

export const updateFornecedoresValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateFornecedor"
          }
        }
      }
    }
  */
  param('id').isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('endereco').isString().withMessage("Endereco inválido"),
  body('cep').isString().withMessage("CEP inválido"),
  body('complemento').isString().withMessage("Complemento inválido"),
  body('telefone').isString().withMessage("Telefone inválido"),
  body('status').isString().withMessage("Status inválido"),
  body('id_cidades').isInt().withMessage("Cidade inválida")
];

export const deleteFornecedoresValidator = [
  param('id').isInt().withMessage("ID inválido")
];
