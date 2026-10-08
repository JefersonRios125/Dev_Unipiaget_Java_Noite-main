

package com.unipiagte.uniescola.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.unipiagte.uniescola.entity.Professor;

public record ProfessorResponseDTO(
    Long id,
    String nome,
    String email,
    String especialidade,
    BigDecimal salario,
    LocalDate dataContratacao,
    EscolaSimplesDTO escola
) {
    public record EscolaSimplesDTO(Long id, String nome) {}
    
    public static ProfessorResponseDTO fromEntity(Professor entity) {
        EscolaSimplesDTO escolaDTO = entity.getEscola() != null 
            ? new EscolaSimplesDTO(entity.getEscola().getId(), entity.getEscola().getNome())
            : null;
            
        return new ProfessorResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getEmail(),
            entity.getEspecialidade(),
            entity.getSalario(),
            entity.getDataContratacao(),
            escolaDTO
        );
    }
}