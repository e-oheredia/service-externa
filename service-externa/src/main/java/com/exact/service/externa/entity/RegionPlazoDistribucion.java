package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.exact.service.externa.entity.id.RegionPlazoDistribucionId;
import com.fasterxml.jackson.annotation.JsonFilter;

@Entity
@Table(name="region_plazo_distribucion")
public class RegionPlazoDistribucion implements Serializable {

	@EmbeddedId
	private RegionPlazoDistribucionId id;
	

	@MapsId("regionId")
	@JoinColumn(name="region_id")
	private Long region;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("plazoDistribucionId")
	@JoinColumn(name="plazo_distribucion_id")
	private PlazoDistribucion plazoDistribucion;
	
	@Column(name="tiempo_envio")
	private int tiempoEnvio;

	
	public RegionPlazoDistribucionId getId() {
		return id;
	}



	public void setId(RegionPlazoDistribucionId id) {
		this.id = id;
	}


	public Long getRegion() {
		return region;
	}



	public void setRegion(Long region) {
		this.region = region;
	}



	public PlazoDistribucion getPlazoDistribucion() {
		return plazoDistribucion;
	}



	public void setPlazoDistribucion(PlazoDistribucion plazoDistribucion) {
		this.plazoDistribucion = plazoDistribucion;
	}



	public int getTiempoEnvio() {
		return tiempoEnvio;
	}



	public void setTiempoEnvio(int tiempoEnvio) {
		this.tiempoEnvio = tiempoEnvio;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

