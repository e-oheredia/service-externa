package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="seguimiento_documento")
public class SeguimientoDocumento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seguimiento_documento_id")
	private Long id;
	private String observacion;
	@Column(name="link_imagen")
	private String linkImagen;
	@Column(nullable = false)
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Lima")
	private Date fecha;
	@Column(nullable = false, name = "usuario_id")
	private Long usuarioId;
	@ManyToOne(optional=false, targetEntity= EstadoDocumento.class)
	@JoinColumn(name="estado_documento_id")
	@JsonFilter("estadoDocumentoFilter")
	private EstadoDocumento estadoDocumento;

	@ManyToOne(optional = false)
	@JoinColumn(name="documento_id")
	@JsonIgnore
	private Documento documento;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="motivo_estado_id")
	private MotivoEstado motivoEstado;
	
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@Transient
	private Map<String, Object> usuario;	
	
	@PrePersist
	public void prePersist() {
		if(fecha == null){
			fecha = new Date();
		}
	}
	
	public SeguimientoDocumento() {}

	public SeguimientoDocumento(Long usuarioId, EstadoDocumento estadoDocumento) {
		this.usuarioId = usuarioId;
		this.estadoDocumento = estadoDocumento;
	}
	
	public SeguimientoDocumento(Long usuarioId, EstadoDocumento estadoDocumento, String observacion) {
		this.usuarioId = usuarioId;
		this.estadoDocumento = estadoDocumento;
		this.observacion = observacion;
	}
	
	
	public MotivoEstado getMotivoEstado() {
		return motivoEstado;
	}
	public void setMotivoEstado(MotivoEstado motivoEstado) {
		this.motivoEstado = motivoEstado;
	}
	public EstadoDocumento getEstadoDocumento() {
		return estadoDocumento;
	}
	public void setEstadoDocumento(EstadoDocumento estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;	}
	
	public String getLinkImagen() {
		return linkImagen;
	}
	public void setLinkImagen(String linkImagen) {
		this.linkImagen = linkImagen;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Map<String, Object> getUsuario() {
		return usuario;
	}
	public void setUsuario(Map<String, Object> usuario) {
		this.usuario = usuario;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
