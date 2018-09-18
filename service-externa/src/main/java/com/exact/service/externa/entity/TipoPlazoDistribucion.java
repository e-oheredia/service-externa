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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tipo_plazo_distribucion")
public class TipoPlazoDistribucion implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tipo_plazo_distribucion_id")
	private Long id;
	@Column(nullable=false)
	private String nombre;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tipoPlazoDistribucion")
	@JsonIgnore
	private Set<PlazoDistribucion> plazosDistribucion;
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
	public Set<PlazoDistribucion> getPlazosDistribucion() {
		return plazosDistribucion;
	}
	public void setPlazosDistribucion(Set<PlazoDistribucion> plazosDistribucion) {
		this.plazosDistribucion = plazosDistribucion;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
