import categorias from "../models/categorias.model.js";
import { validationResult } from "express-validator";

export default class categoriasController {
  static async index(_, res) {
    const categorias = await categorias.findMany();
    res.json(categorias);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const categorias = await categorias.create({
      data: req.body,
    });
    res.json(categorias);
  }

  static async show(req, res) {
    const categorias = await categorias.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!categorias) {
      return res.statu(404).json({ message: "Categoria não encontrada" });
    }
    res.json(categorias);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const categorias = await categorias.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!categorias) {
      return res.status(404).json({ message: "Categoria não encontrada" });
    }
    const updatedcategorias = await categorias.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedcategorias);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const categorias = await categorias.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!categorias) {
      return res.status(404).json({ message: "Categoria não encontrada" });
    }
    await categorias.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Categoria deletada com sucesso" });
  }
}