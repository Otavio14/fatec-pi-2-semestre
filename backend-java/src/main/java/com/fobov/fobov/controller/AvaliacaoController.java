package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Avaliacao;
import com.fobov.fobov.repository.AvaliacoesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController implements Crud<Avaliacao, Integer> {
    private final AvaliacoesRepository AVALIACAO_REPOSITORY;

    public AvaliacaoController(AvaliacoesRepository AVALIACAO_REPOSITORY) {
        this.AVALIACAO_REPOSITORY = AVALIACAO_REPOSITORY;
    }

    @GetMapping
    public List<Avaliacao> findAll() {
        return AVALIACAO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Avaliacao findById(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Avaliacao avalicoes) {
        return AVALIACAO_REPOSITORY.save(avalicoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Avaliacao avalicoes) {
        return AVALIACAO_REPOSITORY.update(id, avalicoes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.delete(id);
    }
}
