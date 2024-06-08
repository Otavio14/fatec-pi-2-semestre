import Avaliacao from "../models/avaliacoes.model.js";
import { validationResult } from "express-validator";

export default class AvaliacaoController {
  static async index(_, res) {
    const avaliacao = await Avaliacao.findMany();
    res.json(avaliacao);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const avaliacao = await avaliacao.create({
      data: req.body,
    });
    res.json(avaliacao);
  }

  static async show(req, res) {
    const avaliacao = await Avaliacao.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!avaliacao) {
      return res.status(404).json({ message: "Avaliação não encontrada" });
    }
    res.json(avaliacao);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const avaliacao = await Avaliacao.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!avaliacao) {
      return res.status(404).json({ message: "Avaliação não encontrada" });
    }
    const updatedavaliacao = await Avaliacao.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedavaliacao);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const avaliacao = await Avaliacao.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!avaliacao) {
      return res.status(404).json({ message: "Avaliação não encontrada" });
    }
    await Avaliacao.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Avaliação deletada com sucesso" });
  }
}
