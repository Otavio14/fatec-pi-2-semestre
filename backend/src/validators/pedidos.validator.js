import { body, param } from 'express-validator';

export const createPedidosValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdatePedido"
          }
        }
      }
    }
  */
  body('id_clientes').isInt().withMessage("Cliente inválido"),
  body('status').isString().withMessage("Status é inválido"),
  body('endereco').isString().withMessage("Endereco inválido"),
  body('dt_pedido').isString().withMessage("Data do pedido inválida"),
  body('total').isNumeric().withMessage("Total inválido")
  
];

export const updatePedidosValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdatePedido"
          }
        }
      }
    }
  */
  param('id').isInt().withMessage("ID inválido"),
  body('id_clientes').isInt().withMessage("Cliente inválido"),
  body('status').isString().withMessage("Status é inválido"),
  body('endereco').isString().withMessage("Endereco inválido"),
  body('dt_pedido').isString().withMessage("Data do pedido inválida"),
  body('total').isNumeric().withMessage("Total inválido")
  
];

export const deletePedidosValidator = [
  param('id').isInt().withMessage("ID inválido")
];
