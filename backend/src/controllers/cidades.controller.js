import Cidade from "../models/cidades.model.js";
import Estado from "../models/estados.model.js";
import { validationResult } from "express-validator";

export default class cidadesController {
  static async index(_, res) {
    const cidades = await Cidade.findMany();
    res.json(cidades);
  }

  static async create(req, res) {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    const cidades = await Cidade.create({
      data: req.body,
    });
    res.json(cidades);
  }

  static async show(req, res) {
    const cidades = await Cidade.findUnique({
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
    const cidades = await Cidade.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cidades) {
      return res.status(404).json({ message: "Cidade não encontrada" });
    }
    const updatedcidades = await Cidade.update({
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
    const cidades = await Cidade.findUnique({
      where: {
        id: parseInt(req.params.id),
      },
    });
    if (!cidades) {
      return res.status(404).json({ message: "Cidade não encontrada" });
    }
    await Cidade.delete({
      where: {
        id: parseInt(req.params.id),
      },
    });
    res.status(204).json({ message: "Cidade deletada com sucesso" });
  }

  //! APAGAR DEPOIS
  static async createAuto(req, res) {
    // const cidades = await Cidade.findMany();
    // res.json(cidades);

    // await Cidade.createMany({
    //   data: req.body,
    // });

    const estados = await Estado.findMany();

    const distritos = await Promise.all(
      estados.map(async (estado) => {
        const response = await fetch(
          `https://servicodados.ibge.gov.br/api/v1/localidades/estados/${estado.sigla}/distritos`
        );
        const data = await response.json();

        return data.map((distrito) => ({
          nome: distrito.nome,
          id_estados: estado.id,
        }));
      })
    );
    console.log(distritos);

    res.json("");
  }
  //! APAGAR DEPOIS
}
