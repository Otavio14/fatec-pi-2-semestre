import { body, param } from "express-validator";

export const createFornecedoresProdutosValidator = [
  body("id_fornecedores").isInt().withMessage("Fornecedor inválido"),
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("preco").isNumeric().withMessage("Preço inválido"),
  body("quantidade").isInt().withMessage("Quantidade inválida"),
];

export const updateFornecedoresProdutosValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body("id_fornecedores").isInt().withMessage("Fornecedor inválido"),
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("preco").isNumeric().withMessage("Preço inválido"),
  body("quantidade").isInt().withMessage("Quantidade inválida"),
];

export const deleteFornecedoresProdutosValidator = [
  param("id").isInt().withMessage("ID inválido"),
];
