package com.exact.service.externa.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ambito_distrito")
public class AmbitoDistrito implements Serializable{

	@Id
	@Column(name="distrito_id")
	private Long distritoId;
	@Column(name="ambito_id")
	private Long ambitoId;
	
	@javax.persistence.Transient
	private Map<String,Object> ambito;
	
	@javax.persistence.Transient
	private Map<String,Object> distrito;
	
	
	
	public Long getDistritoId() {
		return distritoId;
	}



	public void setDistritoId(Long distritoId) {
		this.distritoId = distritoId;
	}



	public Long getAmbitoId() {
		return ambitoId;
	}



	public void setAmbitoId(Long ambitoId) {
		this.ambitoId = ambitoId;
	}



	public Map<String, Object> getAmbito() {
		return ambito;
	}



	public void setAmbito(Map<String, Object> ambito) {
		this.ambito = ambito;
	}



	public Map<String, Object> getDistrito() {
		return distrito;
	}



	public void setDistrito(Map<String, Object> distrito) {
		this.distrito = distrito;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
