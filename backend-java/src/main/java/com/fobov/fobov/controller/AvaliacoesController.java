package com.fobov.fobov.controller;

import com.fobov.fobov.model.Avaliacoes;
import com.fobov.fobov.repository.AvaliacoesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacoesController {
    private final AvaliacoesRepository AVALIACOES_REPOSITORY;

    public AvaliacoesController(AvaliacoesRepository AVALIACOES_REPOSITORY) {
        this.AVALIACOES_REPOSITORY = AVALIACOES_REPOSITORY;
    }

    @GetMapping
    public List<Avalicoes> getAllAvaliacoes() {
        return AVALIACOES_REPOSITORY.findAll();
    }

    @GetMapping("/{id_avaliacoes}")
    public Avalicoes getAvalicoesById(@PathVariable int id_avaliacoes) {
        return AVALIACOES_REPOSITORY.findById(id_avaliacoes);
    }

    @PostMapping
    public boolean createAvalicoes(@RequestBody Avalicoes avalicoes) {
        return AVALIACOES_REPOSITORY.save(avalicoes);
    }

    @PutMapping("/{id_avaliacoes}")
    public boolean updateAvalicoes(@PathVariable int id_avaliacoes, @RequestBody Avalicoes avalicoes) {
        return AVALIACOES_REPOSITORY.update(id_avaliacoes, avalicoes);
    }

    @DeleteMapping("/{id_avaliacoes}")
    public boolean deleteAvalicoes(@PathVariable int id_avaliacoes) {
        return AVALIACOES_REPOSITORY.delete(id_avaliacoes);
    }
}
