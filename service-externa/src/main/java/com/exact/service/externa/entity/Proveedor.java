package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="proveedor")
public class Proveedor implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="proveedor_id")
	private Long id;
	
	@Column(name="nombre",nullable=false)
	private String nombre;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="proveedor_plazo_distribucion", joinColumns = { @JoinColumn(name = "proveedor_id") },
    inverseJoinColumns = { @JoinColumn(name = "plazo_distribucion_id") })
	private Set<PlazoDistribucion> plazosDistribucion;
	
	public Set<PlazoDistribucion> getPlazosDistribucion() {
		return plazosDistribucion;
	}

	public void setPlazosDistribucion(Set<PlazoDistribucion> plazosDistribucion) {
		this.plazosDistribucion = plazosDistribucion;
	}



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



	private static final long serialVersionUID = 1L;
}
