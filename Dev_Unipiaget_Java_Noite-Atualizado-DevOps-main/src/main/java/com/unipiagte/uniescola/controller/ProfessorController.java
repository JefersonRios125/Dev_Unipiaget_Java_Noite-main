

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

import com.unipiagte.uniescola.dto.ProfessorDTO;
import com.unipiagte.uniescola.dto.ProfessorResponseDTO;
import com.unipiagte.uniescola.service.ProfessorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {
    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listar(
            @RequestParam(required = false) Long escolaId,
            @RequestParam(required = false) String especialidade) {
        
        List<ProfessorResponseDTO> professores;
        if (escolaId != null) professores = service.buscarPorEscola(escolaId);
        else if (especialidade != null) professores = service.buscarPorEspecialidade(especialidade);
        else professores = service.listarTodos();
        
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> salvar(@Valid @RequestBody ProfessorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProfessorDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}