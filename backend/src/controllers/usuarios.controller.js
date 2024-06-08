import Usuario from "../models/usuario.model.js";
import { validationResult } from "express-validator";
import * as jose from "jose";

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
      return res.status(404).json({ message: "Usuário não encontrado" });
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

  static async login(req, res) {
    const usuario = await Usuario.findUnique({
      where: {
        email: req.body.email,
        senha: req.body.senha,
      },
    });
    if (!usuario) {
      return res.status(403).json({ message: "Dados incorretos" });
    }

    const secret = new TextEncoder().encode(process.env.JWT_SECRET);
    const jwt = await new jose.SignJWT(usuario)
      .setProtectedHeader({ alg: "HS256" })
      .setIssuedAt()
      .setIssuer("urn:example:issuer")
      .setAudience("urn:example:audience")
      .setExpirationTime("20h")
      .sign(secret);

    res.json({ token: jwt });
  }
}
