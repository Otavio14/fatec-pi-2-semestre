package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ClienteCupom;
import com.fobov.fobov.repository.ClienteCupomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes-cupons")
public class ClienteCupomController implements Crud<ClienteCupom, Integer> {
    private final ClienteCupomRepository CLIENNTE_CUPOM_REPOSITORY;

    public ClienteCupomController(
            ClienteCupomRepository CLIENNTE_CUPOM_REPOSITORY) {
        this.CLIENNTE_CUPOM_REPOSITORY = CLIENNTE_CUPOM_REPOSITORY;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    @GetMapping
    @RouteLevel(1)
    public List<ClienteCupom> findAll() {
        return CLIENNTE_CUPOM_REPOSITORY.findAll();
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    @GetMapping("/{id}")
    @RouteLevel(1)
    public ClienteCupom findById(@PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.findById(id);
    }

    /**
     * Cadastrar um novo registro
     *
     * @param clienteCupom - Dados do registro
     * @return resposta da operacao
     */
    @PostMapping
    @RouteLevel(1)
    public ResponseEntity<String> save(@RequestBody ClienteCupom clienteCupom) {
        return CLIENNTE_CUPOM_REPOSITORY.save(clienteCupom);
    }

    /**
     * Alterar um registro
     *
     * @param id           - ID do registro
     * @param clienteCupom - Dados do registro
     * @return resposta da operacao
     */
    @PutMapping("/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody
    ClienteCupom clienteCupom) {
        return CLIENNTE_CUPOM_REPOSITORY.update(id, clienteCupom);
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
        return CLIENNTE_CUPOM_REPOSITORY.delete(id);
    }

    /**
     * Listar todos os registros com um ID de cliente
     *
     * @param id - ID do cliente
     * @return lista de registros
     */
    @PostMapping("/cliente/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> checkByClienteId(
            @RequestBody ClienteCupom clienteCupom, @PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.checkByClienteId(clienteCupom, id);
    }

    /**
     * Usar um cupom em um pedido
     *
     * @param clienteCupom - Dados do cupom e do cliente
     * @param id           - ID do cliente
     * @return resposta da operacao
     */
    @PostMapping("/usar/{id}")
    @RouteLevel(1)
    public ResponseEntity<String> saveByClienteId(
            @RequestBody ClienteCupom clienteCupom, @PathVariable Integer id) {
        return CLIENNTE_CUPOM_REPOSITORY.saveByClienteId(clienteCupom, id);
    }
}
