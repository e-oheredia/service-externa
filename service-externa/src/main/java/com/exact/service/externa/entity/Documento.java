package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "documento")
public class Documento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "documento_id")
	private Long id;
	
	@Column(name = "documento_autogenerado")
	private String documentoAutogenerado;
	
	@Column(name = "distrito_id", nullable = false)
	private Long distritoId;
	@Column(name = "razon_social_destino")
	private String razonSocialDestino;
	@Column(name = "contacto_destino")
	private String contactoDestino;
	@Column(nullable = false)
	private String direccion;
	private String referencia;
	private String telefono;
	private boolean recepcionado;

	@Column(name = "nro_documento")
	private String nroDocumento;
	
	@Column(name = "codigo_devolucion")
	private String codigoDevolucion;

	public Set<DocumentoGuia> getDocumentosGuia() {
		return documentosGuia;
	}


	public void setDocumentosGuia(Set<DocumentoGuia> documentosGuia) {
		this.documentosGuia = documentosGuia;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "documento")
	private Set<SeguimientoDocumento> seguimientosDocumento;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "documento")
	@JsonFilter("documentosGuiaFilter")
	@JsonProperty("documentosGuia")
	private Set<DocumentoGuia> documentosGuia;

	@ManyToOne(fetch = FetchType.EAGER, optional=false)	
	@JoinColumn(name="envio_id")
	@JsonFilter("envioFilter")
	@JsonProperty("envio")
	private Envio envio;

	@Transient
	private Map<String, Object> distrito;
	
	@PrePersist
	public void prePersist() {
		this.recepcionado=false;
	}
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="documento_tipo_devolucion", joinColumns = { @JoinColumn(name = "documento_id") },
    inverseJoinColumns = { @JoinColumn(name = "tipo_devolucion_id") })
	private Set<TipoDevolucion> tiposDevolucion;


	public Set<TipoDevolucion> getTiposDevolucion() {
		return tiposDevolucion;
	}


	public void setTiposDevolucion(Set<TipoDevolucion> tiposDevolucion) {
		this.tiposDevolucion = tiposDevolucion;
	}


	public String getCodigoDevolucion() {
		return codigoDevolucion;
	}


	public void setCodigoDevolucion(String codigoDevolucion) {
		this.codigoDevolucion = codigoDevolucion;
	}


	public boolean isRecepcionado() {
		return recepcionado;
	}

	public void setRecepcionado(boolean recepcionado) {
		this.recepcionado = recepcionado;
	}

	public Envio getEnvio() {
		return envio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}

	public Map<String, Object> getDistrito() {
		return distrito;
	}

	public void setDistrito(Map<String, Object> distrito) {
		this.distrito = distrito;
		this.distritoId = Long.valueOf(distrito.get("id").toString());
	}

	@JsonIgnore
	public Long getDistritoId() {
		return distritoId;
	}

	@JsonSetter
	public void setDistritoId(Long distritoId) {
		this.distritoId = distritoId;
	}

	public Documento() {
		seguimientosDocumento = new HashSet<SeguimientoDocumento>();
	}

	public void addSeguimientoDocumento(SeguimientoDocumento seguimientoDocumento) {
		seguimientosDocumento.add(seguimientoDocumento);
	}

	public Set<SeguimientoDocumento> getSeguimientosDocumento() {
		return seguimientosDocumento;
	}

	public void setSeguimientosDocumento(Set<SeguimientoDocumento> seguimientosDocumento) {
		this.seguimientosDocumento = seguimientosDocumento;
	}

	public String getDocumentoAutogenerado() {
		return documentoAutogenerado;
	}

	public void setDocumentoAutogenerado(String documento_autogenerado) {
		this.documentoAutogenerado = documento_autogenerado;
	}

	public String getRazonSocialDestino() {
		return razonSocialDestino;
	}

	public void setRazonSocialDestino(String razonSocialDestino) {
		this.razonSocialDestino = razonSocialDestino;
	}

	public String getContactoDestino() {
		return contactoDestino;
	}

	public void setContactoDestino(String contactoDestino) {
		this.contactoDestino = contactoDestino;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	
	@JsonIgnore
	public SeguimientoDocumento getUltimoSeguimientoDocumento() {
		return (this.getSeguimientosDocumento().stream().max(Comparator.comparing(SeguimientoDocumento::getFecha))
		.orElseThrow(NoSuchElementException::new));		
	}
	
	@JsonIgnore
	public SeguimientoDocumento getSeguimientoDocumentoByEstadoId(Long estadoId) {
		Optional<SeguimientoDocumento> sd =  (this.getSeguimientosDocumento().stream().filter(sg -> sg.getEstadoDocumento().getId()==estadoId).findFirst());	
		if(sd.isPresent()) {
			return sd.get();
		}
		return null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
