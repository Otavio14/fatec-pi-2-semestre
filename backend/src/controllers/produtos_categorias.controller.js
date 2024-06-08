import ProdutoCategoria from "../models/produtos_categorias.model.js";
import { validationResult } from "express-validator";

export default class produtos_categoriasController {
  static async index(_, res) {
    const produtos_categorias = await ProdutoCategoria.findMany();
    res.json(produtos_categorias);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_categorias = await ProdutoCategoria.create({
      data: req.body,
    });
    res.json(produtos_categorias);
  }

  static async show(req, res) {
    const produtos_categorias = await ProdutoCategoria.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_categorias) {
      return res
        .status(404)
        .json({ message: "Produtos_Categoria não encontrado" });
    }
    res.json(produtos_categorias);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_categorias = await ProdutoCategoria.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_categorias) {
      return res
        .status(404)
        .json({ message: "Produtos_Categoria não encontrado" });
    }
    const updatedprodutos_categorias = await ProdutoCategoria.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedprodutos_categorias);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_categorias = await ProdutoCategoria.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_categorias) {
      return res
        .status(404)
        .json({ message: "Produtos_Categoria não encontrado" });
    }
    await ProdutoCategoria.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res
      .status(204)
      .json({ message: "Produtos_Categoria deletado com sucesso" });
  }
}
