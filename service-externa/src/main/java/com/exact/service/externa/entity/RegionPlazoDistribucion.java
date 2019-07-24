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

import com.exact.service.externa.entity.id.RegionPlazoDistribucionId;

@Entity
@Table(name="region_plazo_distribucion")
public class RegionPlazoDistribucion implements Serializable {

	@EmbeddedId
	private RegionPlazoDistribucionId id;

	@Transient
	private Long regionId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="plazo_distribucion_id")
	@MapsId("plazoDistribucionId")
	private PlazoDistribucion plazoDistribucion;

	public RegionPlazoDistribucionId getId() {
		return id;
	}

	public void setId(RegionPlazoDistribucionId id) {
		this.id = id;
	}





	public Long getRegionId() {
		return regionId;
	}





	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}





	public PlazoDistribucion getPlazoDistribucion() {
		return plazoDistribucion;
	}





	public void setPlazoDistribucion(PlazoDistribucion plazoDistribucion) {
		this.plazoDistribucion = plazoDistribucion;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

