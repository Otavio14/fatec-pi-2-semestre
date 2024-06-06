import Usuario from "../models/usuario.model.js";
import { validationResult } from "express-validator";

export default class UsuarioController {
  static async index(_, res) {
    const usuarios = await Usuario.findMany();
    res.json(usuarios);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const usuario = await Usuario.create({
      data: req.body,
    });
    res.json(usuario);
  }

  static async show(req, res) {
    const usuario = await Usuario.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!usuario) {
      return res.statu(404).json({ message: "Usuário não encontrado" });
    }
    res.json(usuario);
  }

  static async update(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const usuario = await Usuario.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!usuario) {
      return res.status(404).json({ message: "Usuário não encontrado" });
    }
    const updatedUsuario = await Usuario.update({
      where: {
        id: parseInt(req.params.id),
      },
      data: req.body,
    });
    res.json(updatedUsuario);
  }

  static async delete(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const usuario = await Usuario.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!usuario) {
      return res.status(404).json({ message: "Usuário não encontrado" });
    }
    await Usuario.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Usuário deletado com sucesso" });
  }
}
