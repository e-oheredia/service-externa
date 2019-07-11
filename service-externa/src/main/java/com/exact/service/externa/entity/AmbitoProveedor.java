package com.exact.service.externa.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.exact.service.externa.entity.id.AmbitoProveedorId;

@Entity
@Table(name="ambito_proveedor")
public class AmbitoProveedor implements Serializable{
	
	@EmbeddedId
	private AmbitoProveedorId id;
	
	@Transient
	private Long ambitoId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="proveedor_id")
	@MapsId("proveedorId")
	private Proveedor proveedores;
	
	public AmbitoProveedorId getId() {
		return id;
	}
	
	public void setId(AmbitoProveedorId id) {
		this.id = id;
	}

	public Long getAmbitoId() {
		return ambitoId;
	}

	public void setAmbitoId(Long ambitoId) {
		this.ambitoId = ambitoId;
	}

	public Proveedor getProveedores() {
		return proveedores;
	}

	public void setProveedores(Proveedor proveedores) {
		this.proveedores = proveedores;
	}

	private static final long serialVersionUID = 1L;
	
}
