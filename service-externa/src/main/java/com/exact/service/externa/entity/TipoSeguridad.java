package com.exact.service.externa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo_seguridad")
public class TipoSeguridad {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tipo_seguridad_id")
	private Long id;	
	@Column(nullable=false, unique=true)
	private String nombre;
	@Column(nullable=false)
	private boolean activo;
		

	public TipoSeguridad(Long id, String nombre, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}
	

	public TipoSeguridad() {
	}




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
	
	
	
}
