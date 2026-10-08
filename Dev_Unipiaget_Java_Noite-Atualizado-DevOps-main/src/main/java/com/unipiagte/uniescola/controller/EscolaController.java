




package com.unipiagte.uniescola.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unipiagte.uniescola.dto.EscolaDTO;
import com.unipiagte.uniescola.dto.EscolaResponseDTO;
import com.unipiagte.uniescola.service.EscolaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/escolas")
public class EscolaController {
    private final EscolaService service;

    public EscolaController(EscolaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EscolaResponseDTO>> listar(@RequestParam(required = false) String nome) {
        List<EscolaResponseDTO> escolas = (nome != null) 
            ? service.buscarPorNome(nome) 
            : service.listarTodos();
        return ResponseEntity.ok(escolas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscolaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EscolaResponseDTO> salvar(@Valid @RequestBody EscolaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscolaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EscolaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}