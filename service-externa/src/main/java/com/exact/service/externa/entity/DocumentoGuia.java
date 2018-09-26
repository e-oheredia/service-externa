package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Date;
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

import com.exact.service.externa.entity.id.DocumentoGuiaId;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="documento_guia")
public class DocumentoGuia implements Serializable {	

	@EmbeddedId
	private DocumentoGuiaId id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("documentoId")
	@JoinColumn(name="documento_id")
	private Documento documento;	
		
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("guiaId")
	@JoinColumn(name="guia_id")
	@JsonIgnore
	private Guia guia;
	
	@Column(name="validado")
	private boolean validado;
	
	@Column(name="fecha_asociacion")
	private Date fechaAsociacion;
	
	
	
	public DocumentoGuiaId getId() {
		return id;
	}



	public void setId(DocumentoGuiaId id) {
		this.id = id;
	}



	public Documento getDocumento() {
		return documento;
	}



	public void setDocumento(Documento documento) {
		this.documento = documento;
	}



	public Guia getGuia() {
		return guia;
	}



	public void setGuia(Guia guia) {
		this.guia = guia;
	}



	public boolean isValidado() {
		return validado;
	}



	public void setValidado(boolean validado) {
		this.validado = validado;
	}



	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}



	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
