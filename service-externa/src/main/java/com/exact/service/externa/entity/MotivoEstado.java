package com.exact.service.externa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="motivo_estado")
public class MotivoEstado {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="motivo_estado_id")
	private Long id;
	
	@Column(nullable=false)
	private String nombre;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="estado_documento_id")
	@JsonIgnore
	private EstadoDocumento estadoDocumento;

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

	public EstadoDocumento getEstadoDocumento() {
		return estadoDocumento;
	}

	public void setEstadoDocumento(EstadoDocumento estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}
	
	
}
