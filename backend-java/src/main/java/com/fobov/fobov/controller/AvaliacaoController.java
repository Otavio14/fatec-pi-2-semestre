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

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(2)
    public List<Avaliacao> findAll() {
        return AVALIACAO_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(2)
    public Avaliacao findById(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param avalicao - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(1)
    public ResponseEntity<String> save(@RequestBody Avaliacao avalicao) {
        return AVALIACAO_REPOSITORY.save(avalicao);
    }

    /**
     * Alterar um registro
     *
     * @param id        - ID do registro
     * @param avaliacao - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Avaliacao avaliacao) {
        return AVALIACAO_REPOSITORY.update(id, avaliacao);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.delete(id);
    }

    /**
     * Listar todos os registros com um ID de produto
     *
     * @param id - ID do produto
     * @return lista de registros
     */
    @GetMapping("/produto/{id}")
    @RouteLevel(0)
    public List<Avaliacao> findAllByProdutoId(@PathVariable Integer id) {
        return AVALIACAO_REPOSITORY.findAllByProdutoId(id);
    }
}
