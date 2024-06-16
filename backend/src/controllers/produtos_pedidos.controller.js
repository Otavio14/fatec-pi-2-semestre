import ProdutoPedido from "../models/produtos_pedidos.model.js";
import { validationResult } from "express-validator";

export default class produtos_pedidosController {
  static async index(_, res) {
    const produtos_pedidos = await ProdutoPedido.findMany();
    res.json(produtos_pedidos);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_pedidos = await ProdutoPedido.create({
      data: req.body,
    });
    res.json(produtos_pedidos);
  }

  static async createMultiple(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    await ProdutoPedido.deleteMany({
      where: {
        id_pedidos: parseInt(req.body[0].id_pedidos),
      },
    });
    const produtos_pedidos = await ProdutoPedido.createMany({
      data: req.body,
    });
    res.json(produtos_pedidos);
  }

  static async show(req, res) {
    const produtos_pedidos = await ProdutoPedido.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_pedidos) {
      return res
        .status(404)
        .json({ message: "Produtos_Pedidos não encontrado" });
    }
    res.json(produtos_pedidos);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_pedidos = await ProdutoPedido.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_pedidos) {
      return res
        .status(404)
        .json({ message: "Produtos_Pedidos não encontrado" });
    }
    const updatedprodutos_pedidos = await ProdutoPedido.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedprodutos_pedidos);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_pedidos = await ProdutoPedido.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_pedidos) {
      return res
        .status(404)
        .json({ message: "Produtos_Pedidos não encontrado" });
    }
    await ProdutoPedido.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Produtos_Pedidos deletado com sucesso" });
  }
}
