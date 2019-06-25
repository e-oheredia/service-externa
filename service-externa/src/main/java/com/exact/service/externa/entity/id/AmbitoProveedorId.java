package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class AmbitoProveedorId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="ambito_id")
	private Long ambitoId;
	
	@Column(name="proveedor_id")
	private Long proveedorId;

	
	
	public AmbitoProveedorId() {
		super();
	}

	public AmbitoProveedorId(Long ambitoId, Long proveedorId) {
		super();
		this.ambitoId = ambitoId;
		this.proveedorId = proveedorId;
	}

	public Long getAmbitoId() {
		return ambitoId;
	}

	public void setAmbitoId(Long ambitoId) {
		this.ambitoId = ambitoId;
	}

	public Long getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}




}

