import { body, param } from 'express-validator';

export const createFornecedoresValidator = [
  body('nome').isString().withMessage("Nome inválido"),
  body('Endereco').isString().withMessage("Endereco inválido"),
  body('cep').isString().withMessage("CEP inválido"),
  body('complemento').isString().withMessage("Complemento inválido"),
  body('telefone').isString().withMessage("Telefone inválido"),
  body('status').isString().withMessage("Status inválido"),
  body('cidade').isInt().withMessage("Cidade inválida")
];

export const updateFornecedoresValidator = [
  param('id').isInt().withMessage("ID inválido"),
  body('nome').isString().withMessage("Nome inválido"),
  body('Endereco').isString().withMessage("Endereco inválido"),
  body('cep').isString().withMessage("CEP inválido"),
  body('complemento').isString().withMessage("Complemento inválido"),
  body('telefone').isString().withMessage("Telefone inválido"),
  body('status').isString().withMessage("Status inválido"),
  body('cidade').isInt().withMessage("Cidade inválida")
];

export const deleteFornecedoresValidator = [
  param('id').isInt().withMessage("ID inválido")
];
