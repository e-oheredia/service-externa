package com.exact.service.externa.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="envio_masivo")
public class EnvioMasivo extends Envio {
	
	@Column(name="masivo_autogenerado")
	private String masivoAutogenerado;
	
	@Column(name="sede_id_masivo")
	private Long sedeIdMasivo;
	
	@Transient
	private Map<String, Object> sede;
	
	
	
	public Long getSedeId() {
		return sedeIdMasivo;
	}

	public void setSedeId(Long sedeId) {
		this.sedeIdMasivo = sedeId;
	}

	public Map<String, Object> getSede() {
		return sede;
	}

	public void setSede(Map<String, Object> sede) {
		this.sede = sede;
		this.sedeIdMasivo = Long.valueOf(sede.get("id").toString());
	}

	public String getMasivoAutogenerado() {
		return masivoAutogenerado;
	}

	public void setMasivoAutogenerado(String masivoAutogenerado) {
		this.masivoAutogenerado = masivoAutogenerado;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
