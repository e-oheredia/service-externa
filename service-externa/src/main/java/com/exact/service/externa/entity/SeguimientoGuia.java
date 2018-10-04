package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="seguimiento_guia")
public class SeguimientoGuia implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="seguimiento_guia_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="guia_id")
	@JsonIgnore
	private Guia guia;
	
	@Column(nullable = false, name = "usuario_id")
	private Long usuarioId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="estado_guia_id")
	private EstadoGuia estadoGuia;
	
	@Column(name="fecha")
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Lima")
	private Date fecha;
	
	@Transient
	private Map<String, Object> usuario;	
	
	@PrePersist
	private void prePersist() {
		fecha = new Date();
	}
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Guia getGuia() {
		return guia;
	}



	public void setGuia(Guia guia) {
		this.guia = guia;
	}



	public Long getUsuarioId() {
		return usuarioId;
	}



	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}



	public EstadoGuia getEstadoGuia() {
		return estadoGuia;
	}



	public void setEstadoGuia(EstadoGuia estadoGuia) {
		this.estadoGuia = estadoGuia;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
