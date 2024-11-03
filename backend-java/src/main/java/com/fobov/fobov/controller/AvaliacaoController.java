package com.fobov.fobov.controller;

import com.fobov.fobov.model.Avaliacao;
import com.fobov.fobov.repository.AvaliacoesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    private final AvaliacoesRepository AVALIACAO_REPOSITORY;

    public AvaliacaoController(AvaliacoesRepository AVALIACAO_REPOSITORY) {
        this.AVALIACAO_REPOSITORY = AVALIACAO_REPOSITORY;
    }

    @GetMapping
    public List<Avaliacao> getAllAvaliacoes() {
        return AVALIACAO_REPOSITORY.findAll();
    }

    @GetMapping("/{id_avaliacoes}")
    public Avaliacao getAvalicoesById(@PathVariable int id_avaliacoes) {
        return AVALIACAO_REPOSITORY.findById(id_avaliacoes);
    }

    @PostMapping
    public boolean createAvalicoes(@RequestBody Avaliacao avalicoes) {
        return AVALIACAO_REPOSITORY.save(avalicoes);
    }

    @PutMapping("/{id_avaliacoes}")
    public boolean updateAvalicoes(@PathVariable int id_avaliacoes, @RequestBody Avaliacao avalicoes) {
        return AVALIACAO_REPOSITORY.update(id_avaliacoes, avalicoes);
    }

    @DeleteMapping("/{id_avaliacoes}")
    public boolean deleteAvalicoes(@PathVariable int id_avaliacoes) {
        return AVALIACAO_REPOSITORY.delete(id_avaliacoes);
    }
}
