import Estado from "../models/estados.model.js";
import { validationResult } from "express-validator";

export default class estadosController {
  static async index(_, res) {
    const estados = await Estado.findMany({ orderBy: { nome: "asc" } });
    res.json(estados);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const estados = await Estado.create({
      data: req.body,
    });
    res.json(estados);
  }

  static async show(req, res) {
    const estados = await Estado.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!estados) {
      return res.status(404).json({ message: "Estado não encontrado" });
    }
    res.json(estados);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const estados = await Estado.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!estados) {
      return res.status(404).json({ message: "Estado não encontrado" });
    }
    const updatedestados = await Estado.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedestados);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const estados = await Estado.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!estados) {
      return res.status(404).json({ message: "Estado não encontrado" });
    }
    await Estado.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Estado deletado com sucesso" });
  }
}
