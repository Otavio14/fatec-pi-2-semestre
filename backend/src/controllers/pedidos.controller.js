import pedidos from "../models/pedidos.model.js";
import { validationResult } from "express-validator";

export default class pedidosController {
  static async index(_, res) {
    const pedidos = await pedidos.findMany();
    res.json(pedidos);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const pedidos = await pedidos.create({
      data: req.body,
    });
    res.json(pedidos);
  }

  static async show(req, res) {
    const pedidos = await pedidos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!pedidos) {
      return res.statu(404).json({ message: "Pedido não encontrado" });
    }
    res.json(pedidos);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const pedidos = await pedidos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!pedidos) {
      return res.status(404).json({ message: "Pedido não encontrado" });
    }
    const updatedpedidos = await pedidos.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedpedidos);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const pedidos = await pedidos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!pedidos) {
      return res.status(404).json({ message: "Pedido não encontrado" });
    }
    await pedidos.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Pedido deletado com sucesso" });
  }
}
