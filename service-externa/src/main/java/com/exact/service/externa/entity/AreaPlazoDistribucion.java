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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="area_plazo_distribucion_id")
	private Long id;
	@Column(name="area_id")
	private Long areaId;
	@Column(name="plazo_id")
	private Long plazoId;
	@Transient
	private Map<String, Object> area;
	@Transient
	private Map<String, Object> plazos;
	
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
	
	public Long getPlazoId() {
		return plazoId;
	}
	public void setPlazoId(Long plazoId) {
		this.plazoId = plazoId;
	}
	public Map<String, Object> getPlazos() {
		return plazos;
	}
	public void setPlazos(Map<String, Object> plazos) {
		this.plazoId=Long.valueOf(plazos.get("id").toString());
		this.plazos = plazos;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}
