package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AreaPlazoDistribucionId implements Serializable {
	
	@Column(name="area_id")
	private Long areaId;
	@Column(name="plazo_distribucion_id")
	private Long plazoDistribucionId;
	public AreaPlazoDistribucionId() {
		super();
	}
	public AreaPlazoDistribucionId(Long areaId, Long plazoDistribucionId) {
		super();
		this.areaId = areaId;
		this.plazoDistribucionId = plazoDistribucionId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getPlazoDistribucionId() {
		return plazoDistribucionId;
	}
	public void setPlazoDistribucionId(Long plazoDistribucionId) {
		this.plazoDistribucionId = plazoDistribucionId;
	}	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
