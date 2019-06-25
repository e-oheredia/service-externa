package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.exact.service.externa.entity.id.AmbitoPlazoDistribucionId;

@Entity
@Table(name="ambito_plazo_distribucion")
public class AmbitoPlazoDistribucion implements Serializable{

	@EmbeddedId
	private AmbitoPlazoDistribucionId id;
	
	@Transient
	private Long ambitoId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="plazo_distribucion_id")
	@MapsId("plazoDistribucionId")
	private PlazoDistribucion plazoDistribucion;
	
	
	public AmbitoPlazoDistribucionId getId() {
		return id;
	}



	public void setId(AmbitoPlazoDistribucionId id) {
		this.id = id;
	}


	public PlazoDistribucion getPlazoDistribucion() {
		return plazoDistribucion;
	}



	public void setPlazoDistribucion(PlazoDistribucion plazoDistribucion) {
		this.plazoDistribucion = plazoDistribucion;
	}
	
	


	
















	public Long getAmbitoId() {
		return ambitoId;
	}



	public void setAmbitoId(Long ambitoId) {
		this.ambitoId = ambitoId;
	}





















	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
}
