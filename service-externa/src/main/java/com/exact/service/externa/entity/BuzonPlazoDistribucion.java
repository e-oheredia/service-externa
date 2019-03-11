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
@Table(name="buzon_plazo_distribucion")
public class BuzonPlazoDistribucion implements Serializable {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="buzon_plazo_distribucion_id")
	private Long id;
	@Column(name="buzon_id")
	private Long buzonId;
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="plazo_distribucion_id")
//	private PlazoDistribucion plazoDistribucion;
	@Column(name="plazo_id")
	private Long plazoId;
	@Transient
	private Map<String, Object> buzon;	
	@Transient
	private Map<String, Object> plazos;
	
	
	public Long getBuzonId() {
		return buzonId;
	}

	public void setBuzonId(Long buzonId) {
		this.buzonId = buzonId;
	}

	public Map<String, Object> getBuzon() {
		return buzon;
	}

	public void setBuzon(Map<String, Object> buzon) {
		this.buzonId=Long.valueOf(buzon.get("id").toString());
		this.buzon = buzon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
