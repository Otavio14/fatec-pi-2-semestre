import { body, param } from 'express-validator';

export const createPedidosValidator = [
  body('cliente').isInt().withMessage("Cliente inválido"),
  body('status').isString().withMessage("Status é inválido"),
  body('endereco').isString().withMessage("Endereco inválido"),
  body('dtPedido').isString().withMessage("Data do pedido inválida"),
  body('total').isNumeric().withMessage("Total inválido")
  
];

export const updatePedidosValidator = [
  param('id').isInt().withMessage("ID inválido"),
  body('cliente').isInt().withMessage("Cliente inválido"),
  body('status').isString().withMessage("Status é inválido"),
  body('endereco').isString().withMessage("Endereco inválido"),
  body('dtPedido').isString().withMessage("Data do pedido inválida"),
  body('total').isNumeric().withMessage("Total inválido")
  
];

export const deletePedidosValidator = [
  param('id').isInt().withMessage("ID inválido")
];