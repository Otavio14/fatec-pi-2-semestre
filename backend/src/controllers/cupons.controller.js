import cupons from "../models/cupons.model.js";
import { validationResult } from "express-validator";

export default class cuponsController {
  static async index(_, res) {
    const cupons = await cupons.findMany();
    res.json(cupons);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const cupons = await cupons.create({
      data: req.body,
    });
    res.json(cupons);
  }

  static async show(req, res) {
    const cupons = await cupons.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cupons) {
      return res.statu(404).json({ message: "Cupom não encontrado" });
    }
    res.json(cupons);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const cupons = await cupons.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cupons) {
      return res.status(404).json({ message: "Cupom não encontrado" });
    }
    const updatedcupons = await cupons.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedcupons);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const cupons = await cupons.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cupons) {
      return res.status(404).json({ message: "Cupom não encontrado" });
    }
    await cupons.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Cupom deletado com sucesso" });
  }
}