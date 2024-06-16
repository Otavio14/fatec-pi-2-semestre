import { body, param } from "express-validator";

export const createProdutoValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto"
          }
        }
      }
    }
  */
  body("nome").isString().withMessage("Nome inválido"),
  body("preco").isNumeric().withMessage("Preço inválido"),
  body('estoque').isInt().withMessage("Número em estoque inválido"),
  body('descricao').isString().withMessage("Descrição inválida"),
  body('dt_validade').isString().withMessage("validade inválida"),
  body('imagem').isString().withMessage("url de imagem inválida"),
];

export const updateProdutoValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body("nome").isString().withMessage("Nome inválido"),
  body("preco").isNumeric().withMessage("Preço inválido"),
  body('estoque').isInt().withMessage("Número em estoque inválido"),
  body('descricao').isString().withMessage("Descrição inválida"),
  body('dt_validade').isString().withMessage("validade inválida"),
  body('imagem').isString().withMessage("url de imagem inválida"),
];

export const deleteProdutoValidator = [
  param("id").isInt().withMessage("Id inválido")
];