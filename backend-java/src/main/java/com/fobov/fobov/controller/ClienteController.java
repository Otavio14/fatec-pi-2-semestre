package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cliente;
import com.fobov.fobov.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController implements Crud<Cliente, Integer> {
    private final ClienteRepository CLIENTE_REPOSITORY;

    public ClienteController(ClienteRepository CLIENTE_REPOSITORY) {
        this.CLIENTE_REPOSITORY = CLIENTE_REPOSITORY;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    public List<Cliente> findAll() {
        return CLIENTE_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Integer id) {
        return CLIENTE_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param cliente - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Cliente cliente) {
        return CLIENTE_REPOSITORY.save(cliente);
    }

    /**
     * Alterar um registro
     *
     * @param id      - ID do registro
     * @param cliente - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Cliente cliente) {
        return CLIENTE_REPOSITORY.update(id, cliente);
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return CLIENTE_REPOSITORY.delete(id);
    }
}
