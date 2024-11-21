package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cidade;
import com.fobov.fobov.repository.CidadeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController implements Crud<Cidade, Integer> {
    private final CidadeRepository CIDADE_REPOSITORY;

    public CidadeController(CidadeRepository CIDADE_REPOSITORY) {
        this.CIDADE_REPOSITORY = CIDADE_REPOSITORY;
    }

    @GetMapping
    public List<Cidade> findAll() {
        return CIDADE_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Cidade findById(@PathVariable Integer id) {
        return CIDADE_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Cidade cidade) {
        return CIDADE_REPOSITORY.save(cidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Cidade cidade) {
        return CIDADE_REPOSITORY.update(id, cidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CIDADE_REPOSITORY.delete(id);
    }

    @GetMapping("/estado/{idEstado}")
    public List<Cidade> findAllByEstado(@PathVariable Integer idEstado) {
        return CIDADE_REPOSITORY.findAllByEstado(idEstado);
    }
}
