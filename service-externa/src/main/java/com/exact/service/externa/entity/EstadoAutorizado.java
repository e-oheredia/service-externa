package com.exact.service.externa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estado_autorizado")
public class EstadoAutorizado implements Serializable{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="estado_autorizado_id")
	private Long id;
	
	@Column(nullable=false)
	private String nombre;

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


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
