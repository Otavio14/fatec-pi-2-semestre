import { body, param } from 'express-validator';

export const createProdutosValidator = [
  body('produto').isInt().withMessage("Produto inválido"),
  body('cliente').isInt().withMessage("Cliente inválido"),
  body('nota').isInt().withMessage("Nota inválida"),
  body('comentario').isString().withMessage("Comentário inválido"),
  body('dtAvaliacao').isString().withMessage("Data inválida")
];

export const updateProdutosValidator = [
  param('id').isInt().withMessage("ID inválido"),
  body('produto').isInt().withMessage("Produto inválido"),
  body('cliente').isInt().withMessage("Cliente inválido"),
  body('nota').isInt().withMessage("Nota inválida"),
  body('comentario').isString().withMessage("Comentário inválido"),
  body('dtAvaliacao').isString().withMessage("Data inválida")
];

export const deleteProdutosValidator = [
  param('id').isInt().withMessage("ID inválido")
];