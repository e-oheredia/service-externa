package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Guia")
@JsonFilter("GuiaFilter")
public class Guia implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="guia_id")
	private Long id;
	
	@Column(name="numero_guia")
	private String numeroGuia;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="plazo_distribucion_id")
	private PlazoDistribucion plazoDistribucion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_servicio_id")
	private TipoServicio tipoServicio;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_seguridad_id")
	private TipoSeguridad tipoSeguridad;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="proveedor_id")
	private Proveedor proveedor;
	
	@Column(name="producto_id")
	private Long productoId;
	
	@Column(name="tipo_clasificacion_id")
	private Long tipoClasificacionId;
	
	public Long getProductoId() {
		return productoId;
	}


	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}


	public Long getTipoClasificacionId() {
		return tipoClasificacionId;
	}


	public void setTipoClasificacionId(Long tipoClasificacionId) {
		this.tipoClasificacionId = tipoClasificacionId;
	}



	@Transient
	private Map<String, Object> producto;
	
	@Transient
	private Map<String, Object> clasificacion;	
	
	@Transient
	private int cantidadEntregados;
	
	@Transient
	private int cantidadRezagados;
	
	@Transient
	private int cantidadNoDistribuibles;
	
	@Transient
	private int cantidadPendientes;	
	
	
	

	public int getCantidadEntregados() {
		return cantidadEntregados;
	}


	public void setCantidadEntregados(int cantidadEntregados) {
		this.cantidadEntregados = cantidadEntregados;
	}


	public int getCantidadRezagados() {
		return cantidadRezagados;
	}


	public void setCantidadRezagados(int cantidadRezagados) {
		this.cantidadRezagados = cantidadRezagados;
	}


	public int getCantidadNoDistribuibles() {
		return cantidadNoDistribuibles;
	}


	public void setCantidadNoDistribuibles(int cantidadNoDistribuibles) {
		this.cantidadNoDistribuibles = cantidadNoDistribuibles;
	}


	public int getCantidadPendientes() {
		return cantidadPendientes;
	}


	public void setCantidadPendientes(int cantidadPendientes) {
		this.cantidadPendientes = cantidadPendientes;
	}


	public Map<String, Object> getProducto() {
		return producto;
	}


	public void setProducto(Map<String, Object> producto) {
		this.productoId=Long.valueOf(producto.get("id").toString());		
		this.producto = producto;
	}


	public Map<String, Object> getClasificacion() {
		return clasificacion;
	}


	public void setClasificacion(Map<String, Object> clasificacion) {
		this.tipoClasificacionId = Long.valueOf(clasificacion.get("id").toString());		
		this.clasificacion = clasificacion;
	}



	@Column(name="cantidad_documentos")
	private int cantidadDocumentos;
	

	public int getCantidadDocumentos() {
		return cantidadDocumentos;
	}


	public void setCantidadDocumentos(int cantidadDocumentos) {
		this.cantidadDocumentos = cantidadDocumentos;
	}



	@OneToMany(cascade = CascadeType.ALL, mappedBy = "guia")
	private Set<SeguimientoGuia> seguimientosGuia;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "guia")
	@JsonFilter("documentosGuiaFilter")
	@JsonProperty("documentosGuia")	
	private Set<DocumentoGuia> documentosGuia;	
	
	@Column(name="sede_id")
	private Long sedeId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_guia_id")
	private TipoGuia tipoGuia;
	
	@Transient
	private Map<String, Object> sede;
	

	public TipoGuia getTipoGuia() {
		return tipoGuia;
	}


	public void setTipoGuia(TipoGuia tipoGuia) {
		this.tipoGuia = tipoGuia;
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


	public PlazoDistribucion getPlazoDistribucion() {
		return plazoDistribucion;
	}


	public void setPlazoDistribucion(PlazoDistribucion plazoDistribucion) {
		this.plazoDistribucion = plazoDistribucion;
	}


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


	public Proveedor getProveedor() {
		return proveedor;
	}


	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}


	
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
	


	private static final long serialVersionUID = 1L;
}
