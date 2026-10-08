


package com.unipiagte.uniescola.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, length = 150)
    private String nome;
    
    @Column(length = 150)
    private String email;
    
    @Column(nullable = false)
    private boolean ativo = true;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuarios_perfis", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "perfil")
    private List<String> perfis = new ArrayList<>();
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public List<String> getPerfis() { return perfis; }
    public void setPerfis(List<String> perfis) { this.perfis = perfis; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}