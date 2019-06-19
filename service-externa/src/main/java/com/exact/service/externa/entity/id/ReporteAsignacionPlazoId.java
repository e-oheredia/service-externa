package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class ReporteAsignacionPlazoId implements Serializable{

	@Column(name="area_plazo_distribucion_id")
	private Long areaPlazoDistribucionId;
	
	@Column(name="buzon_plazo_distribucion_id")
	private Long buzonPlazoDistribucionId;

	
	public ReporteAsignacionPlazoId() {
		super();
	}

	public ReporteAsignacionPlazoId(Long areaPlazoDistribucionId, Long buzonPlazoDistribucionId) {
		super();
		this.areaPlazoDistribucionId = areaPlazoDistribucionId;
		this.buzonPlazoDistribucionId = buzonPlazoDistribucionId;
	}

	public Long getAreaPlazoDistribucionId() {
		return areaPlazoDistribucionId;
	}

	public void setAreaPlazoDistribucionId(Long areaPlazoDistribucionId) {
		this.areaPlazoDistribucionId = areaPlazoDistribucionId;
	}

	public Long getBuzonPlazoDistribucionId() {
		return buzonPlazoDistribucionId;
	}

	public void setBuzonPlazoDistribucionId(Long buzonPlazoDistribucionId) {
		this.buzonPlazoDistribucionId = buzonPlazoDistribucionId;
	}
	
	
	
}
