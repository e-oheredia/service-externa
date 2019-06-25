package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AmbitoPlazoDistribucionId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="ambito_id")
	private Long ambitoId;
	
	@Column(name="plazo_distribucion_id")
	private Long plazoDistribucionId;
	
	public AmbitoPlazoDistribucionId() {
		super();
	}

	public AmbitoPlazoDistribucionId(Long ambitoId, Long plazoDistribucionID) {
		super();
		this.ambitoId = ambitoId;
		this.plazoDistribucionId = plazoDistribucionID;
	}

	public Long getAmbitoId() {
		return ambitoId;
	}

	public void setAmbitoId(Long ambitoId) {
		this.ambitoId = ambitoId;
	}

	public Long getPlazoDistribucionID() {
		return plazoDistribucionId;
	}

	public void setPlazoDistribucionID(Long plazoDistribucionID) {
		this.plazoDistribucionId = plazoDistribucionID;
	}
	
	
	
	
}
