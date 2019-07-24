package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="proveedor")
public class Proveedor implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="proveedor_id")
	private Long id;
	
	@Column(name="nombre",nullable=false, unique=true)
	private String nombre;
	
	@Transient
	private Set<Map<String, Object>> ambitos;
	
	@Column(name="activo", nullable=false)
	private boolean activo;
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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

	public Set<Map<String, Object>> getAmbitos() {
		return ambitos;
	}

	public void setAmbitos(Set<Map<String, Object>> ambitos) {
		this.ambitos = ambitos;
	}



	private static final long serialVersionUID = 1L;
}
