package com.deliveryfood.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@EqualsAndHashCode(of = { "id" })
@Table(name = "grupo")
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 60)
	private String nome;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "grupo_id", foreignKey = @ForeignKey(name = "grupo_grupo_permissao_fk")), inverseJoinColumns = @JoinColumn(name = "permissao_id", foreignKey = @ForeignKey(name = "permissao_grupo_permissao_fk")))
	private Set<Permissao> permissoes = new HashSet<>();

	public void adicionarPermissao(Permissao permissao) {
		this.getPermissoes().add(permissao);
	}

	public void removerPermissao(Permissao permissao) {
		this.getPermissoes().remove(permissao);
	}
}
