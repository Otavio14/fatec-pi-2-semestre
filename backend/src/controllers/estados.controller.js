import estados from "../models/estados.model.js";
import { validationResult } from "express-validator";

export default class estadosController {
  static async index(_, res) {
    const estados = await estados.findMany();
    res.json(estados);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const estados = await estados.create({
      data: req.body,
    });
    res.json(estados);
  }

  static async show(req, res) {
    const estados = await estados.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!estados) {
      return res.statu(404).json({ message: "Estado não encontrado" });
    }
    res.json(estados);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const estados = await estados.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!estados) {
      return res.status(404).json({ message: "Estado não encontrado" });
    }
    const updatedestados = await estados.update({
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
    const estados = await estados.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!estados) {
      return res.status(404).json({ message: "Estado não encontrado" });
    }
    await estados.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Estado deletado com sucesso" });
  }
}