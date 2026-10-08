

package com.unipiagte.uniescola.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unipiagte.uniescola.dto.EscolaDTO;
import com.unipiagte.uniescola.dto.EscolaResponseDTO;
import com.unipiagte.uniescola.entity.Escola;
import com.unipiagte.uniescola.exception.BusinessException;
import com.unipiagte.uniescola.exception.ResourceNotFoundException;
import com.unipiagte.uniescola.repository.EscolaRepository;

@Service
public class EscolaService {
    private final EscolaRepository repository;

    public EscolaService(EscolaRepository repository) {
        this.repository = repository;
    }

    public List<EscolaResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(EscolaResponseDTO::fromEntity)
                .toList();
    }

    public EscolaResponseDTO buscarPorId(Long id) {
        Escola escola = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com ID: " + id));
        return EscolaResponseDTO.fromEntity(escola);
    }

    public List<EscolaResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContaining(nome).stream()
                .map(EscolaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public EscolaResponseDTO salvar(EscolaDTO dto) {
        if (dto.cnpj() != null && repository.existsByCnpj(dto.cnpj())) {
            throw new BusinessException("Já existe uma escola com este CNPJ");
        }

        Escola escola = new Escola();
        escola.setNome(dto.nome());
        escola.setCnpj(dto.cnpj());
        escola.setEndereco(dto.endereco());
        
        return EscolaResponseDTO.fromEntity(repository.save(escola));
    }

    @Transactional
    public EscolaResponseDTO atualizar(Long id, EscolaDTO dto) {
        Escola escola = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com ID: " + id));

        if (dto.cnpj() != null && !dto.cnpj().equals(escola.getCnpj()) 
            && repository.existsByCnpj(dto.cnpj())) {
            throw new BusinessException("CNPJ já cadastrado em outra escola");
        }

        escola.setNome(dto.nome());
        escola.setCnpj(dto.cnpj());
        escola.setEndereco(dto.endereco());
        
        return EscolaResponseDTO.fromEntity(repository.save(escola));
    }

    @Transactional
    public void deletar(Long id) {
        Escola escola = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com ID: " + id));
        repository.delete(escola);
    }
}