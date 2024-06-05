import produtos from "../models/produtos.model.js";
import { validationResult } from "express-validator";

export default class produtosController {
  static async index(_, res) {
    const produtos = await Produtos.findMany();
    res.json(produtos);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos = await Produtos.create({
      data: req.body,
    });
    res.json(produtos);
  }

  static async show(req, res) {
    const produtos = await Produtos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos) {
      return res.statu(404).json({ message: "Produtos_Pedido não encontrado" });
    }
    res.json(produtos);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos = await Produtos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos) {
      return res.status(404).json({ message: "Produtos_Pedido não encontrado" });
    }
    const updatedprodutos = await Produtos.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedprodutos);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos = await Produtos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos) {
      return res.status(404).json({ message: "Produtos_Pedido não encontrado" });
    }
    await Produtos.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Produtos_Pedido deletado com sucesso" });
  }
}
