package com.exact.service.externa.entity;

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
@Table(name="tipo_estado_documento")
public class TipoEstadoDocumento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tipo_estado_documento_id")
	private Long id;
	@Column(name="nombre",nullable=false)
	private String nombre;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tipoEstadoDocumento")
	private Set<EstadoDocumento> estadoDocumento;

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

	public Set<EstadoDocumento> getEstadoDocumento() {
		return estadoDocumento;
	}

	public void setEstadoDocumento(Set<EstadoDocumento> estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}
	
}
