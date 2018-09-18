package com.exact.service.externa.entity.id;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class BuzonPlazoDistribucionId implements Serializable{
	@Column(name="buzon_id")
	private Long buzonId;
	@Column(name="plazo_distribucion_id")
	private Long plazoDistribucionId;
	public BuzonPlazoDistribucionId() {
	}
	public BuzonPlazoDistribucionId(Long buzonId, Long plazoDistribucionId) {
		this.buzonId = buzonId;
		this.plazoDistribucionId = plazoDistribucionId;
	}
	public Long getBuzonId() {
		return buzonId;
	}
	public void setBuzonId(Long buzonId) {
		this.buzonId = buzonId;
	}
	public Long getPlazoDistribucionId() {
		return plazoDistribucionId;
	}
	public void setPlazoDistribucionId(Long plazoDistribucionId) {
		this.plazoDistribucionId = plazoDistribucionId;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        BuzonPlazoDistribucionId that = (BuzonPlazoDistribucionId) o;
        return Objects.equals(buzonId, that.buzonId) && 
               Objects.equals(plazoDistribucionId, that.plazoDistribucionId);
	}
	@Override
	public int hashCode() {
		return Objects.hash(buzonId, plazoDistribucionId);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
