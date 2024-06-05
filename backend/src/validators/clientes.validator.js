import { body, param } from 'express-validator';

export const createClientesValidator = [
  body('cidade').isInt().withMessage("Cidade inválida"),
  body('cep').isString().withMessage("CEP inválido"),
  body('email').isString().withMessage("E-mail inválido"),
  body('telefone').isString().withMessage("Telefone inválido"),
  body('bairro').isString().withMessage("Bairro inválido"),
  body('numero').isInt().withMessage("Numero inválido"),
  body('endereco').isString().withMessage("Endereço inválido")
];

export const updateClientesValidator = [
  param('id').isInt().withMessage("ID inválido"),
  body('cep').isString().withMessage("CEP inválido"),
  body('email').isString().withMessage("E-mail inválido"),
  body('telefone').isString().withMessage("Telefone inválido"),
  body('bairro').isString().withMessage("Bairro inválido"),
  body('numero').isInt().withMessage("Numero inválido"),
  body('endereco').isString().withMessage("Endereço inválido")
];

export const deleteClientesValidator = [
  param('id').isInt().withMessage("ID inválido")
];
