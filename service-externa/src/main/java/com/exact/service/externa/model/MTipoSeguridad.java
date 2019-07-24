package com.exact.service.externa.model;

import com.exact.service.externa.entity.TipoSeguridad;

public class MTipoSeguridad {

	private Long id;	
	
	private String nombre;
	
	private boolean activo;

	public MTipoSeguridad(Long id, String nombre, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}
	
	

	public MTipoSeguridad(TipoSeguridad tiposeguridad) {
		this.id = tiposeguridad.getId() ;
		this.nombre = tiposeguridad.getNombre();
		this.activo = tiposeguridad.isActivo();
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
}
