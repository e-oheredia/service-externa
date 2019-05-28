package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="estado_documento")
public class EstadoDocumento implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="estado_documento_id")
	private Long id;
	
	@Column(nullable=false)
	private String nombre;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="estadoDocumento")
	private Set<SeguimientoDocumento> seguimientosDocumento;
	
	@ManyToOne(optional=false, targetEntity= TipoEstadoDocumento.class)
	@JoinColumn(name="tipo_estado_documento_id")
	private TipoEstadoDocumento tipoEstadoDocumento;
	
	@ManyToMany(fetch=FetchType.LAZY,  cascade = CascadeType.ALL)
	@JoinTable(name="estado_documento_permitido", joinColumns = { @JoinColumn(name = "estado_documento_id") },
	inverseJoinColumns = { @JoinColumn(name = "estado_documento_permitido_id") })
	@JsonFilter("estadosDocumentoPermitidosFilter")
	@JsonProperty("estadosDocumentoPermitidos")
	private Set<EstadoDocumento> estadosDocumentoPermitidos;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoDocumento")
	private Set<MotivoEstado> motivos;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="resultado_tipo_devolucion", joinColumns = { @JoinColumn(name = "estado_documento_id") },
    inverseJoinColumns = { @JoinColumn(name = "tipo_devolucion_id") })
	private Set<TipoDevolucion> tiposDevolucion;
	
	
	
	public Set<TipoDevolucion> getTiposDevolucion() {
		return tiposDevolucion;
	}

	public void setTiposDevolucion(Set<TipoDevolucion> tiposDevolucion) {
		this.tiposDevolucion = tiposDevolucion;
	}

	public Set<EstadoDocumento> getEstadosDocumentoPermitidos() {
		return estadosDocumentoPermitidos;
	}

	public void setEstadosDocumentoPermitidos(Set<EstadoDocumento> estadosDocumentoPermitidos) {
		this.estadosDocumentoPermitidos = estadosDocumentoPermitidos;
	}

	public EstadoDocumento() {}
	
	public EstadoDocumento(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	

	
	
	public Set<MotivoEstado> getMotivos() {
		return motivos;
	}

	public void setMotivos(Set<MotivoEstado> motivos) {
		this.motivos = motivos;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
