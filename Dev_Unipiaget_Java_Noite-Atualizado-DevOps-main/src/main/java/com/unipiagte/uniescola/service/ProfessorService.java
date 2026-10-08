


package com.unipiagte.uniescola.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unipiagte.uniescola.dto.ProfessorDTO;
import com.unipiagte.uniescola.dto.ProfessorResponseDTO;
import com.unipiagte.uniescola.entity.Escola;
import com.unipiagte.uniescola.entity.Professor;
import com.unipiagte.uniescola.exception.BusinessException;
import com.unipiagte.uniescola.exception.ResourceNotFoundException;
import com.unipiagte.uniescola.repository.EscolaRepository;
import com.unipiagte.uniescola.repository.ProfessorRepository;

@Service
public class ProfessorService {
    private final ProfessorRepository repository;
    private final EscolaRepository escolaRepository;

    public ProfessorService(ProfessorRepository repository, EscolaRepository escolaRepository) {
        this.repository = repository;
        this.escolaRepository = escolaRepository;
    }

    public List<ProfessorResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ProfessorResponseDTO::fromEntity)
                .toList();
    }

    public ProfessorResponseDTO buscarPorId(Long id) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com ID: " + id));
        return ProfessorResponseDTO.fromEntity(professor);
    }

    public List<ProfessorResponseDTO> buscarPorEscola(Long escolaId) {
        return repository.findByEscolaId(escolaId).stream()
                .map(ProfessorResponseDTO::fromEntity)
                .toList();
    }

    public List<ProfessorResponseDTO> buscarPorEspecialidade(String especialidade) {
        return repository.findByEspecialidadeIgnoreCase(especialidade).stream()
                .map(ProfessorResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ProfessorResponseDTO salvar(ProfessorDTO dto) {
        if (dto.email() != null && repository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um professor com este email");
        }

        Escola escola = escolaRepository.findById(dto.escolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada"));

        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setEspecialidade(dto.especialidade());
        professor.setSalario(dto.salario());
        professor.setDataContratacao(dto.dataContratacao());
        professor.setEscola(escola);

        return ProfessorResponseDTO.fromEntity(repository.save(professor));
    }

    @Transactional
    public ProfessorResponseDTO atualizar(Long id, ProfessorDTO dto) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com ID: " + id));

        if (dto.email() != null && !dto.email().equals(professor.getEmail()) 
            && repository.existsByEmail(dto.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        Escola escola = escolaRepository.findById(dto.escolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada"));

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setEspecialidade(dto.especialidade());
        professor.setSalario(dto.salario());
        professor.setDataContratacao(dto.dataContratacao());
        professor.setEscola(escola);

        return ProfessorResponseDTO.fromEntity(repository.save(professor));
    }

    @Transactional
    public void deletar(Long id) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com ID: " + id));
        repository.delete(professor);
    }
}