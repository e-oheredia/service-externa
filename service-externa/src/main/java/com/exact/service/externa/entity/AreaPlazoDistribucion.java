package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="area_plazo_distribucion")
public class AreaPlazoDistribucion implements Serializable {
	
	@Id
	@Column(name="area_id")
	private Long areaId;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="plazo_distribucion_id")
	private PlazoDistribucion plazoDistribucion;
	@Transient
	private Map<String, Object> area;
	@Column(name="ruta_autorizacion")
	private String rutaAutorizacion;
	
	
	public String getRutaAutorizacion() {
		return rutaAutorizacion;
	}
	public void setRutaAutorizacion(String rutaAutorizacion) {
		this.rutaAutorizacion = rutaAutorizacion;
	}
	public PlazoDistribucion getPlazoDistribucion() {
		return plazoDistribucion;
	}
	public void setPlazoDistribucion(PlazoDistribucion plazoDistribucion) {
		this.plazoDistribucion = plazoDistribucion;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Map<String, Object> getArea() {
		return area;
	}
	public void setArea(Map<String, Object> area) {
		this.areaId=Long.valueOf(area.get("id").toString());
		this.area = area;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}
