package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cupom;
import com.fobov.fobov.repository.CupomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupons")
public class CupomController implements Crud<Cupom, Integer> {
    private final CupomRepository CUPOM_REPOSITORY;

    public CupomController(CupomRepository CUPOM_REPOSITORY) {
        this.CUPOM_REPOSITORY = CUPOM_REPOSITORY;
    }

    @GetMapping
    public List<Cupom> findAll() {
        return CUPOM_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Cupom findById(@PathVariable Integer id) {
        return CUPOM_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Cupom cupom) {
        return CUPOM_REPOSITORY.save(cupom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Cupom cupom) {
        return CUPOM_REPOSITORY.update(id, cupom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CUPOM_REPOSITORY.delete(id);
    }
}
