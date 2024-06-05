import { body, param } from 'express-validator';

export const createProdutosCategoriasValidator = [
  body('produto').isInt().withMessage("Produto inválido"),
  body('categorias').isInt().withMessage("Categoria inválida")
];

export const updateProdutosCategoriasValidator = [
  param('id').isInt().withMessage("ID inválido"),
  body('produto').isInt().withMessage("Produto inválido"),
  body('categorias').isInt().withMessage("Categoria inválida")
];

export const deleteProdutosCategoriasValidator = [
  param('id').isInt().withMessage("ID inválido")
];
