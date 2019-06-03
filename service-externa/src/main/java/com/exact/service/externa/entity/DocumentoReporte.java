package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "documento_reporte")
public class DocumentoReporte implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "documento_reporte_id")
	private Long id;
	
	@Column(name="documento_id")
	private Long documentoId;
	
	@Column(name="tiempo_entrega")
	private Long tiempoEntrega;
	
	@Column(name="estado_documento_id")
	private Long estadoDocumento;
	
	@Column(name="proveedor_id")
	private Long proveedorId;
	
	@Column(name="plazo_id")
	private Long plazoId;
	
	@Column(name="estado_cargo")
	private Long estadoCargo;
	
	@Column(nullable = false)
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Lima")
	private Date fecha;
	
	

	public Date getFecha() {
		return fecha;
	}




	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getDocumentoId() {
		return documentoId;
	}




	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}




	public Long getTiempoEntrega() {
		return tiempoEntrega;
	}




	public void setTiempoEntrega(Long tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}




	public Long getEstadoDocumento() {
		return estadoDocumento;
	}




	public void setEstadoDocumento(Long estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}




	public Long getProveedorId() {
		return proveedorId;
	}




	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}




	public Long getPlazoId() {
		return plazoId;
	}




	public void setPlazoId(Long plazoId) {
		this.plazoId = plazoId;
	}




	public Long getEstadoCargo() {
		return estadoCargo;
	}




	public void setEstadoCargo(Long estadoCargo) {
		this.estadoCargo = estadoCargo;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
