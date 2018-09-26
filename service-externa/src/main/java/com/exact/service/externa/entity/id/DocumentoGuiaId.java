package com.exact.service.externa.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentoGuiaId implements Serializable{

	@Column(name="guia_id")
	private Long guiaId;
	
	@Column(name="documento_id")
	private Long documentoId;
	
	public DocumentoGuiaId() {
		super();
	}
	
	public DocumentoGuiaId(Long guiaId, Long documentoId) {
		super();
		this.guiaId = guiaId;
		this.documentoId = documentoId;
	}
	
	public Long getGuiaId() {
		return guiaId;
	}

	public void setGuiaId(Long guiaId) {
		this.guiaId = guiaId;
	}

	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
}
