import fornecedores_produtos from "../models/fornecedores_produtos.model.js";
import { validationResult } from "express-validator";

export default class fornecedores_produtosController {
  static async index(_, res) {
    const fornecedores_produtos = await fornecedores_produtos.findMany();
    res.json(fornecedores_produtos);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const fornecedores_produtos = await fornecedores_produtos.create({
      data: req.body,
    });
    res.json(fornecedores_produtos);
  }

  static async show(req, res) {
    const fornecedores_produtos = await fornecedores_produtos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!fornecedores_produtos) {
      return res.statu(404).json({ message: "Forncedor_Produto não encontrado" });
    }
    res.json(fornecedores_produtos);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const fornecedores_produtos = await fornecedores_produtos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!fornecedores_produtos) {
      return res.status(404).json({ message: "Forncedor_Produto não encontrado" });
    }
    const updatedfornecedores_produtos = await fornecedores_produtos.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedfornecedores_produtos);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const fornecedores_produtos = await fornecedores_produtos.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!fornecedores_produtos) {
      return res.status(404).json({ message: "Forncedor_Produto não encontrado" });
    }
    await fornecedores_produtos.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Fornecedor_Produto deletado com sucesso" });
  }
}
