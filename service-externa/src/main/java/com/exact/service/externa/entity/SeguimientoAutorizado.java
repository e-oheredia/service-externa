package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "seguimiento_autorizado")
public class SeguimientoAutorizado implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seguimiento_autorizado_id")
	private Long id;
	
	@Column(name="fecha")
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Lima")
	private Date fecha;
	
	@Column(nullable = false, name = "usuario_id")
	private Long usuarioId;
	
	@Column(nullable = true, name = "nombre_usuario")
	private String nombreUsuario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="envio_id")
	@JsonIgnore
	private Envio envio;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="estado_autorizado_id")
	private EstadoAutorizado estadoAutorizado;
	
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public EstadoAutorizado getEstadoAutorizado() {
		return estadoAutorizado;
	}

	public void setEstadoAutorizado(EstadoAutorizado estadoAutorizado) {
		this.estadoAutorizado = estadoAutorizado;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
