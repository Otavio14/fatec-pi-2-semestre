import { body, param } from 'express-validator';

export const createProdutosPedidosValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto_Pedido"
          }
        }
      }
    }
  */
  body('pedidos').isInt().withMessage("Pedido inválido"),
  body('produtos').isInt().withMessage("Produto inválido"),
  body('quantidade').isInt().withMessage("Quantidade inválida"), 
  body('precos').isNumeric().withMessage("Preço inválido")
];

export const updateProdutosPedidosValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto_Pedido"
          }
        }
      }
    }
  */
  param('id').isInt().withMessage("ID inválido"),
  body('pedidos').isInt().withMessage("Pedido inválido"),
  body('produtos').isInt().withMessage("Produto inválido"),
  body('quantidade').isInt().withMessage("Quantidade inválida"), 
  body('precos').isNumeric().withMessage("Preço inválido")
];

export const deleteProdutosPedidosValidator = [
  param('id').isInt().withMessage("ID inválido")
];
