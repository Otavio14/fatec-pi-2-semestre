import produtos_pedidos from "../models/produtos_pedidos.model.js";
import { validationResult } from "express-validator";

export default class produtos_pedidosController {
  static async index(_, res) {
    const produtos_pedidos = await Produtos_Pedidos.findMany();
    res.json(produtos_pedidos);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_pedidos = await Produtos_Pedidos.create({
      data: req.body,
    });
    res.json(produtos_pedidos);
  }

  static async show(req, res) {
    const produtos_pedidos = await Produtos_Pedidos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_pedidos) {
      return res.statu(404).json({ message: "Produtos_Pedidos não encontrado" });
    }
    res.json(produtos_pedidos);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const produtos_pedidos = await Produtos_Pedidos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_pedidos) {
      return res.status(404).json({ message: "Produtos_Pedidos não encontrado" });
    }
    const updatedprodutos_pedidos = await Produtos_Pedidos.update({
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
    const produtos_pedidos = await Produtos_Pedidos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!produtos_pedidos) {
      return res.status(404).json({ message: "Produtos_Pedidos não encontrado" });
    }
    await Produtos_Pedidos.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Produtos_Pedidos deletado com sucesso" });
  }
}
