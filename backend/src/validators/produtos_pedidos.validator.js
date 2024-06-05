import { body, param } from 'express-validator';

export const createProdutosPedidosValidator = [
  body('pedidos').isInt().withMessage("Pedido inválido"),
  body('produtos').isInt().withMessage("Produto inválido"),
  body('quantidade').isInt().withMessage("Quantidade inválida"), 
  body('precos').isNumeric().withMessage("Preço inválido")
];

export const updateProdutosPedidosValidator = [
  param('id').isInt().withMessage("ID inválido"),
  body('pedidos').isInt().withMessage("Pedido inválido"),
  body('produtos').isInt().withMessage("Produto inválido"),
  body('quantidade').isInt().withMessage("Quantidade inválida"), 
  body('precos').isNumeric().withMessage("Preço inválido")
];

export const deleteProdutosPedidosValidator = [
  param('id').isInt().withMessage("ID inválido")
];
