package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RegionPlazoDistribucionId implements Serializable {


	@Column(name="region_id")
	private Long regionId;
	
	@Column(name="plazo_distribucion_id")
	private Long plazoDistribucionId;

	public RegionPlazoDistribucionId(Long regionId, Long plazoDistribucion) {
		super();
		this.regionId = regionId;
		this.plazoDistribucionId = plazoDistribucion;
	}

	public RegionPlazoDistribucionId() {
		super();
	}

	
	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
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
