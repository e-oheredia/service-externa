package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SubambitoPlazoDistribucionId implements Serializable {


	@Column(name="subambito_id")
	private Long subambitoId;
	
	@Column(name="plazo_distribucion_id")
	private Long plazoDistribucionId;

	public SubambitoPlazoDistribucionId(Long subambitoId, Long plazoDistribucion) {
		super();
		this.subambitoId = subambitoId;
		this.plazoDistribucionId = plazoDistribucion;
	}

	public SubambitoPlazoDistribucionId() {
		super();
	}

	public Long getSubambitoId() {
		return subambitoId;
	}

	public void setSubambitoId(Long subambitoId) {
		this.subambitoId = subambitoId;
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
