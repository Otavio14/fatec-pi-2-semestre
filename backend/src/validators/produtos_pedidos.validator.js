import { body, param } from "express-validator";

export const createProdutosPedidosValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto_Pedido"
          }
        }
      }
    }
  */
  body("id_pedidos").isInt().withMessage("Pedido inválido"),
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("quantidade").isInt().withMessage("Quantidade inválida"), 
  body("precos").isNumeric().withMessage("Preço inválido"),
];

export const createMultipleProdutosPedidosValidator = [
  body().isArray().withMessage("Deve ser um array"),
  body("*").isObject().withMessage("Deve ser um objeto"),
  body("*").custom((value) => {
    if (
      !value.id_pedidos ||
      !value.id_produtos ||
      !value.quantidade ||
      !value.preco
    ) {
      throw new Error("Objeto inválido");
    }
    return true;
  }),
];

export const updateProdutosPedidosValidator = [
    /* 
    #swagger.requestBody = {
      required: true,
      content: {
        "application/json": {
          schema: {
            $ref: "#/components/schemas/AddOrUpdateProduto_Pedido"
          }
        }
      }
    }
  */
  param("id").isInt().withMessage("ID inválido"),
  body("id_pedidos").isInt().withMessage("Pedido inválido"),
  body("id_produtos").isInt().withMessage("Produto inválido"),
  body("quantidade").isInt().withMessage("Quantidade inválida"), 
  body("precos").isNumeric().withMessage("Preço inválido"),
];

export const deleteProdutosPedidosValidator = [
  param('id').isInt().withMessage("ID inválido"),
];
