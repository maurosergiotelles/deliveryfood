package com.deliveryfood.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "cozinha")
@Entity
@EqualsAndHashCode(of = { "id" })
public class Cozinha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@JsonIgnore
	private Long id;

//	@JsonProperty("titulo")
	@Column(nullable = false)
	private String nome;

	@OneToMany(mappedBy = "cozinha")
	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();
}
