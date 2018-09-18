package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BuzonTipoSeguridadId implements Serializable {
	
	@Column(name="buzon_id")
	private Long buzonId;
	@Column(name="tipo_seguridad_id")
	private Long tipoSeguridadId;
	public BuzonTipoSeguridadId() {
		super();
	}
	public BuzonTipoSeguridadId(Long buzonId, Long tipoSeguridadId) {
		super();
		this.buzonId = buzonId;
		this.tipoSeguridadId = tipoSeguridadId;
	}
	public Long getBuzonId() {
		return buzonId;
	}
	public void setBuzonId(Long buzonId) {
		this.buzonId = buzonId;
	}
	public Long getTipoSeguridadId() {
		return tipoSeguridadId;
	}
	public void setTipoSeguridadId(Long tipoSeguridadId) {
		this.tipoSeguridadId = tipoSeguridadId;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
