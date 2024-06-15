import { body, param } from "express-validator";

export const createProdutosCategoriasValidator = [
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("id_categorias").isInt().withMessage("Categoria inválida"),
];

export const updateProdutosCategoriasValidator = [
  param("id").isInt().withMessage("ID inválido"),
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("id_categorias").isInt().withMessage("Categoria inválida"),
];

export const deleteProdutosCategoriasValidator = [
  param("id").isInt().withMessage("ID inválido"),
];
