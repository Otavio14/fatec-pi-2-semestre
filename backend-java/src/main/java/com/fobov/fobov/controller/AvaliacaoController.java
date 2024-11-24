package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Avaliacao;
import com.fobov.fobov.repository.AvaliacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController implements Crud<Avaliacao, Integer> {
    private final AvaliacaoRepository AVALIACAO_REPOSITORY;

    public AvaliacaoController(AvaliacaoRepository AVALIACAO_REPOSITORY) {
        this.AVALIACAO_REPOSITORY = AVALIACAO_REPOSITORY;
    }

    @GetMapping
    @RouteLevel(2)
    public List<Avaliacao> findAll() {
        return AVALIACAO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    @RouteLevel(2)
    public Avaliacao findById(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.findById(id);
    }

    @PostMapping
    @RouteLevel(1)
    public ResponseEntity<String> save(@RequestBody Avaliacao avalicoes) {
        return AVALIACAO_REPOSITORY.save(avalicoes);
    }

    @PutMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Avaliacao avalicoes) {
        return AVALIACAO_REPOSITORY.update(id, avalicoes);
    }

    @DeleteMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.delete(id);
    }

    @GetMapping("/produto/{id}")
    @RouteLevel(0)
    public List<Avaliacao> findAllByProdutoId(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.findAllByProdutoId(id);
    }
}
