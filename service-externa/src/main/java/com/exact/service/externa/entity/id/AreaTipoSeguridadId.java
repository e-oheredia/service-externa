package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AreaTipoSeguridadId implements Serializable {
	
	@Column(name="area_id")
	private Long areaId;
	@Column(name="tipo_seguridad_id")
	private Long tipoSeguridadId;
	public AreaTipoSeguridadId() {
		super();
	}
	public AreaTipoSeguridadId(Long areaId, Long tipoSeguridadId) {
		super();
		this.areaId = areaId;
		this.tipoSeguridadId = tipoSeguridadId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
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
