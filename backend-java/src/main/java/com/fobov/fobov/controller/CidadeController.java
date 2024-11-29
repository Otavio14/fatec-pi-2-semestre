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

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    public List<Cidade> findAll() {
        return CIDADE_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    public Cidade findById(@PathVariable Integer id) {
        return CIDADE_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param cidade - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Cidade cidade) {
        return CIDADE_REPOSITORY.save(cidade);
    }

    /**
     * Alterar um registro
     *
     * @param id     - ID do registro
     * @param cidade - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Cidade cidade) {
        return CIDADE_REPOSITORY.update(id, cidade);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CIDADE_REPOSITORY.delete(id);
    }

    /**
     * Listar todos os registros com um ID de estado
     *
     * @param idEstado - ID do estado
     * @return lista de registros
     */
    @GetMapping("/estado/{idEstado}")
    public List<Cidade> findAllByEstado(@PathVariable Integer idEstado) {
        return CIDADE_REPOSITORY.findAllByEstado(idEstado);
    }
}
