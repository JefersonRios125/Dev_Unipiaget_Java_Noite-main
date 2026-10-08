
package com.unipiagte.uniescola.dto;

import java.time.LocalDateTime;

import com.unipiagte.uniescola.entity.Escola;

public record EscolaResponseDTO(
    Long id,
    String nome,
    String cnpj,
    String endereco,
    LocalDateTime dataCriacao
) {
    public static EscolaResponseDTO fromEntity(Escola entity) {
        return new EscolaResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getCnpj(),
            entity.getEndereco(),
            entity.getDataCriacao()
        );
    }
}