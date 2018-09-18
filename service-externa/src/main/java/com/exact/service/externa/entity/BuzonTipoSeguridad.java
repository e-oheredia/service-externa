package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="buzon_tipo_seguridad")
public class BuzonTipoSeguridad implements Serializable {	
	
	@Id
	@Column(name="buzon_id")
	private Long buzonId;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_seguridad_id")
	private TipoSeguridad tipoSeguridad;
	@Transient
	private Map<String, Object> buzon;
	
	public Long getBuzonId() {
		return buzonId;
	}
	public void setBuzonId(Long buzonId) {
		this.buzonId = buzonId;
	}
	public TipoSeguridad getTipoSeguridad() {
		return tipoSeguridad;
	}
	public void setTipoSeguridad(TipoSeguridad tipoSeguridad) {
		this.tipoSeguridad = tipoSeguridad;
	}
	public Map<String, Object> getBuzon() {
		return buzon;
	}
	public void setBuzon(Map<String, Object> buzon) {
		this.buzon = buzon;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
