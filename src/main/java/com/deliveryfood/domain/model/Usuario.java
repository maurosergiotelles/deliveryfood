package com.deliveryfood.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "usuario")
@EqualsAndHashCode(of = { "id" })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 60)
	private String nome;

	@Column(nullable = false, length = 60)
	private String email;

	@Column(nullable = false, length = 60)
	private String senha;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;

	public boolean senhaCoincideCom(String senha) {
		return this.getSenha().equals(senha);
	}

	public boolean senhaNaoCoincideCom(String senha) {
		return !this.senhaCoincideCom(senha);
	}

	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "usuario_usuario_grupo_fk")), inverseJoinColumns = @JoinColumn(name = "grupo_id", foreignKey = @ForeignKey(name = "usuario_grupo_fk")))
	private List<Grupo> grupos = new ArrayList<>();
}
