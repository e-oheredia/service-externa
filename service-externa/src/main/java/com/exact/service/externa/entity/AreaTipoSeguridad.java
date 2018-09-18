package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="area_tipo_seguridad")
public class AreaTipoSeguridad implements Serializable {
	
	@Id
	@Column(name="area_id")
	private Long areaId;
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("tipoSeguridadId")
	@JoinColumn(name="tipo_seguridad_id")
	private TipoSeguridad tipoSeguridad;
	@Transient
	private Map<String, Object> area;
	
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public TipoSeguridad getTipoSeguridad() {
		return tipoSeguridad;
	}
	public void setTipoSeguridad(TipoSeguridad tipoSeguridad) {
		this.tipoSeguridad = tipoSeguridad;
	}
	public Map<String, Object> getArea() {
		return area;
	}
	public void setArea(Map<String, Object> area) {
		this.area = area;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
