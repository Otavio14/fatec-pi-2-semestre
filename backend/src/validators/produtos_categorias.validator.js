import { body, param } from "express-validator";

export const createProdutosCategoriasValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto_Categoria"
          }
        }
      }
    }
  */
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("id_categorias").isInt().withMessage("Categoria inválida"),
];

export const updateProdutosCategoriasValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto_Categoria"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("id_categorias").isInt().withMessage("Categoria inválida"),
];

export const deleteProdutosCategoriasValidator = [
  param("id").isInt().withMessage("ID inválido"),
];
