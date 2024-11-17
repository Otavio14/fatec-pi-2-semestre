package com.fobov.fobov.controller;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Usuario;
import com.fobov.fobov.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements Crud<Usuario, Integer> {
    private final UsuarioRepository USUARIO_REPOSITORY;

    public UsuarioController(UsuarioRepository USUARIO_REPOSITORY) {
        this.USUARIO_REPOSITORY = USUARIO_REPOSITORY;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> findAll() {
        return USUARIO_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Integer id) {
        return USUARIO_REPOSITORY.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Usuario usuario) {
        return USUARIO_REPOSITORY.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody Usuario usuario) {
        return USUARIO_REPOSITORY.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return USUARIO_REPOSITORY.delete(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        return USUARIO_REPOSITORY.login(usuario);
    }
}
