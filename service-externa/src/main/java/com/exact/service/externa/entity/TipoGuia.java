package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tipo_guia")
public class TipoGuia implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tipo_guia_id")
	private Long id;
	
	@Column(name="nombre",nullable=false)
	private String nombre;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tipoGuia")
	private Set<Guia> guias;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Guia> getGuias() {
		return guias;
	}

	public void setGuias(Set<Guia> guias) {
		this.guias = guias;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
