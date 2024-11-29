package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
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

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(0)
    public List<Cupom> findAll() {
        return CUPOM_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(0)
    public Cupom findById(@PathVariable Integer id) {
        return CUPOM_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param cupom - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(@RequestBody Cupom cupom) {
        return CUPOM_REPOSITORY.save(cupom);
    }

    /**
     * Alterar um registro
     *
     * @param id    - ID do registro
     * @param cupom - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Cupom cupom) {
        return CUPOM_REPOSITORY.update(id, cupom);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CUPOM_REPOSITORY.delete(id);
    }
}
