import { body, param } from 'express-validator';

export const createFornecedoresProdutosValidator = [
  body('fornecedor').isInt().withMessage("Fornecedor inválido"),
  body('produto').isInt().withMessage("Produto inválido"),
  body('preco').isNumeric().withMessage("Preço inválido"),
  body('quantidade').isInt().withMessage("Quantidade inválida")
];

export const updateFornecedoresProdutosValidator = [
  param('id').isInt().withMessage("ID inválido"),
  body('fornecedor').isInt().withMessage("Fornecedor inválido"),
  body('produto').isInt().withMessage("Produto inválido"),
  body('preco').isNumeric().withMessage("Preço inválido"),
  body('quantidade').isInt().withMessage("Quantidade inválida")
];

export const deleteFornecedoresProdutosValidator = [
  param('id').isInt().withMessage("ID inválido")
];
