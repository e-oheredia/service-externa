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
	
	
	public String getMasivoAutogenerado() {
		return masivoAutogenerado;
	}

	public void setMasivoAutogenerado(String masivoAutogenerado) {
		this.masivoAutogenerado = masivoAutogenerado;
	}
	
	
	private static final long serialVersionUID = 1L;
	
}
