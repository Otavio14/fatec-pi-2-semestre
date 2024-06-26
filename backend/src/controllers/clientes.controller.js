import Cliente from "../models/clientes.model.js";
import Pedido from "../models/pedidos.model.js";
import { validationResult } from "express-validator";

export default class clientesController {
  static async index(_, res) {
    const clientes = await Cliente.findMany({
      include: {
        cidade: {
          include: {
            estado: true,
          },
        },
      },
    });
    res.json(clientes);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const clientes = await Cliente.create({
      data: req.body,
    });
    res.json(clientes);
  }

  static async show(req, res) {
    const clientes = await Cliente.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!clientes) {
      return res.status(404).json({ message: "Cliente não encontrado" });
    }
    res.json(clientes);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const clientes = await Cliente.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!clientes) {
      return res.status(404).json({ message: "Cliente não encontrado" });
    }
    const updatedclientes = await Cliente.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedclientes);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }

    const clientes = await Cliente.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!clientes) {
      return res.status(404).json({ message: "Cliente não encontrado" });
    }

    const pedido = await Pedido.findFirst({
      where: {
        id_clientes: parseInt(req.params.id),
      },
    });
    if (pedido) {
      return res
        .status(400)
        .json({ message: "Esse cliente possui um pedido em aberto!" });
    }

    await Cliente.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Cliente deletado com sucesso" });
  }
}
