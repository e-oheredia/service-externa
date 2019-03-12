package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Guia")
public class Guia implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="guia_id")
	private Long id;
	
	@Column(name="numero_guia")
	private String numeroGuia;
	
	@Column(name="plazo_id")
	private Long plazoId;
	
	@Transient
	private Map<String, Object> plazo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_servicio_id")
	private TipoServicio tipoServicio;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_seguridad_id")
	private TipoSeguridad tipoSeguridad;
	
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="proveedor_id")
//	private Proveedor proveedor;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "guia")
	private Set<SeguimientoGuia> seguimientosGuia;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "guia")
	@JsonFilter("documentosGuiaFilter")
	@JsonProperty("documentosGuia")
	private Set<DocumentoGuia> documentosGuia;	
	
	@Column(name="sede_id")
	private Long sedeId;
	
	@Transient
	private Map<String, Object> sede;
	
	@Column(name="proveedor_id")
	private Long proveedorId;
	
	@Transient
	private Map<String, Object> proveedor;
	
	public Long getProveedorId() {
		return proveedorId;
	}


	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}


	public Map<String, Object> getProveedor() {
		return proveedor;
	}


	public void setProveedor(Map<String, Object> proveedor) {
		this.proveedorId=Long.valueOf(proveedor.get("id").toString());
		this.proveedor = proveedor;
	}


	public Map<String, Object> getSede() {
		return sede;
	}


	public void setSede(Map<String, Object> sede) {
		this.sedeId= Long.valueOf(sede.get("id").toString());
		this.sede = sede;
	}


	public Long getSedeId() {
		return sedeId;
	}


	public void setSedeId(Long sedeId) {
		this.sedeId = sedeId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


//	public PlazoDistribucion getPlazoDistribucion() {
//		return plazoDistribucion;
//	}
//
//
//	public void setPlazoDistribucion(PlazoDistribucion plazoDistribucion) {
//		this.plazoDistribucion = plazoDistribucion;
//	}


	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}


	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}


	public TipoSeguridad getTipoSeguridad() {
		return tipoSeguridad;
	}


	public void setTipoSeguridad(TipoSeguridad tipoSeguridad) {
		this.tipoSeguridad = tipoSeguridad;
	}


//	public Proveedor getProveedor() {
//		return proveedor;
//	}
//
//
//	public void setProveedor(Proveedor proveedor) {
//		this.proveedor = proveedor;
//	}


	
	public String getNumeroGuia() {
		return numeroGuia;
	}


	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}


	public Set<SeguimientoGuia> getSeguimientosGuia() {
		return seguimientosGuia;
	}


	public void setSeguimientosGuia(Set<SeguimientoGuia> seguimientosGuia) {
		this.seguimientosGuia = seguimientosGuia;
	}


	public Set<DocumentoGuia> getDocumentosGuia() {
		return documentosGuia;
	}


	public void setDocumentosGuia(Set<DocumentoGuia> documentosGuia) {
		this.documentosGuia = documentosGuia;
	}

	@JsonIgnore
	public SeguimientoGuia getUltimoSeguimientoGuia() {
		return (this.getSeguimientosGuia().stream().max(Comparator.comparing(SeguimientoGuia::getFecha))
		.orElseThrow(NoSuchElementException::new));		
	}
	
	public Long getPlazoId() {
		return plazoId;
	}


	public void setPlazoId(Long plazoId) {
		this.plazoId = plazoId;
	}


	public Map<String, Object> getPlazo() {
		return plazo;
	}


	public void setPlazo(Map<String, Object> plazo) {
		this.plazoId = Long.valueOf(plazo.get("id").toString());
		this.plazo = plazo;
	}



	private static final long serialVersionUID = 1L;
}
