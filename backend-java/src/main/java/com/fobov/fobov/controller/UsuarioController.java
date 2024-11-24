package com.fobov.fobov.controller;

import com.fobov.fobov.config.RouteLevel;
import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Usuario;
import com.fobov.fobov.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements Crud<Usuario, Integer> {
    private final UsuarioRepository USUARIO_REPOSITORY;

    public UsuarioController(UsuarioRepository USUARIO_REPOSITORY) {
        this.USUARIO_REPOSITORY = USUARIO_REPOSITORY;
    }

    @GetMapping
    @RouteLevel(2)
    public List<Usuario> findAll() {
        return USUARIO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    @RouteLevel(2)
    public Usuario findById(@PathVariable Integer id) {
        return USUARIO_REPOSITORY.findById(id);
    }

    @PostMapping
    @RouteLevel(2)
    public ResponseEntity<String> save(@RequestBody Usuario usuario) {
        return USUARIO_REPOSITORY.save(usuario);
    }

    @PutMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Usuario usuario) {
        return USUARIO_REPOSITORY.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    @RouteLevel(2)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return USUARIO_REPOSITORY.delete(id);
    }

    @PostMapping("/login")
    @RouteLevel(0)
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        return USUARIO_REPOSITORY.login(usuario);
    }
}
