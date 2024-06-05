import cidades from "../models/cidades.model.js";
import { validationResult } from "express-validator";

export default class cidadesController {
  static async index(_, res) {
    const cidades = await cidades.findMany();
    res.json(cidades);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const cidades = await cidades.create({
      data: req.body,
    });
    res.json(cidades);
  }

  static async show(req, res) {
    const cidades = await cidades.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cidades) {
      return res.statu(404).json({ message: "Cidade não encontrada" });
    }
    res.json(cidades);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const cidades = await cidades.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cidades) {
      return res.status(404).json({ message: "Cidade não encontrada" });
    }
    const updatedcidades = await cidades.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedcidades);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const cidades = await cidades.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cidades) {
      return res.status(404).json({ message: "Cidade não encontrada" });
    }
    await cidades.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Cidade deletada com sucesso" });
  }
}