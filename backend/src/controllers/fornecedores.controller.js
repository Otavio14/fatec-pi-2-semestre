import Fornecedor from "../models/fornecedores.model.js";
import { validationResult } from "express-validator";

export default class fornecedorController {
  static async index(_, res) {
    const fornecedor = await Fornecedor.findMany();
    res.json(fornecedor);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const fornecedor = await Fornecedor.create({
      data: req.body,
    });
    res.json(fornecedor);
  }

  static async show(req, res) {
    const fornecedor = await Fornecedor.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!fornecedor) {
      return res.status(404).json({ message: "Fornecedor não encontrado" });
    }
    res.json(fornecedor);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const fornecedor = await Fornecedor.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!fornecedor) {
      return res.status(404).json({ message: "Fornecedor não encontrado" });
    }
    const updatedfornecedor = await Fornecedor.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedfornecedor);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const fornecedor = await Fornecedor.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!fornecedor) {
      return res.status(404).json({ message: "Fornecedor não encontrado" });
    }
    await Fornecedor.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Fornecedor deletado com sucesso" });
  }
}
