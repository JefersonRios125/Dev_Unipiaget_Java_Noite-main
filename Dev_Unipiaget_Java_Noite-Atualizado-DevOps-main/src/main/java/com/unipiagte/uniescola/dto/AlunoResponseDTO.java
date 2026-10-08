

package com.unipiagte.uniescola.dto;

import java.time.LocalDate;

import com.unipiagte.uniescola.entity.Aluno;

public record AlunoResponseDTO(
    Long id,
    String nome,
    String matricula,
    LocalDate dataNascimento,
    String email,
    EscolaSimplesDTO escola
) {
    public record EscolaSimplesDTO(Long id, String nome) {}
    
    public static AlunoResponseDTO fromEntity(Aluno entity) {
        AlunoResponseDTO.EscolaSimplesDTO escolaDTO = entity.getEscola() != null
            ? new AlunoResponseDTO.EscolaSimplesDTO(entity.getEscola().getId(), entity.getEscola().getNome())
            : null;
            
        return new AlunoResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getMatricula(),
            entity.getDataNascimento(),
            entity.getEmail(),
            escolaDTO
        );
    }
}