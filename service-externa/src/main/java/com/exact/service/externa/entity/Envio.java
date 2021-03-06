package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="envio")

	@Inheritance(
	    strategy = InheritanceType.JOINED
	)
@JsonFilter("EnvioFilter")
public class Envio implements Serializable {	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="envio_id")
	private Long id;
	@Column(name="buzon_id")
	private Long buzonId;
	@Column(name="ruta_autorizacion")
	private String rutaAutorizacion;
	@Column(name="tipo_clasificacion_id")
	private Long tipoClasificacionId;
	@Column(name="sede_id")
	private Long sedeId;
	@Column(name="producto_id")
	private Long productoId;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="plazo_distribucion_id")
	private PlazoDistribucion plazoDistribucion;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_servicio_id")
	private TipoServicio tipoServicio;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_seguridad_id")
	private TipoSeguridad tipoSeguridad;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="envio", cascade = { CascadeType.PERSIST })
	@JsonFilter("documentosFilter")
	@JsonProperty("documentos")
	private Set<Documento> documentos;
	@Transient
	private Map<String, Object> clasificacion;			
	@Transient
	private Map<String, Object> buzon;
	@Transient
	private Map<String, Object> sede;
	@Transient
	private Map<String, Object> producto;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_envio_id")
	private TipoEnvio tipoEnvio;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "envio")
	private Set<SeguimientoAutorizado> seguimientosAutorizado;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "envio")
	@JsonProperty("inconsistenciasDocumento")
	private Set<Inconsistencia> inconsistenciasDocumento;
	
	@Column(name="fecha")
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Lima")
	private Date fecha;
	
	@Transient
	private int cantidadDocumentos;
	
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@PrePersist
	private void prePersist() {
		fecha = new Date();
	}

	public int getCantidadDocumentos() {
		return cantidadDocumentos;
	}

	public void setCantidadDocumentos(int cantidadDocumentos) {
		this.cantidadDocumentos = cantidadDocumentos;
	}

	public Set<SeguimientoAutorizado> getSeguimientosAutorizado() {
		return seguimientosAutorizado;
	}

	public void setSeguimientosAutorizado(Set<SeguimientoAutorizado> seguimientosAutorizado) {
		this.seguimientosAutorizado = seguimientosAutorizado;
	}

	public Set<Inconsistencia> getInconsistenciasDocumento() {
		return inconsistenciasDocumento;
	}

	public void setInconsistenciasDocumento(Set<Inconsistencia> inconsistenciasDocumento) {
		this.inconsistenciasDocumento = inconsistenciasDocumento;
	}

	public TipoEnvio getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(TipoEnvio tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public Long getTipoClasificacionId() {
		return tipoClasificacionId;
	}

	public void setTipoClasificacionId(Long tipoClasificacionId) {
		this.tipoClasificacionId = tipoClasificacionId;
	}

	public Map<String, Object> getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(Map<String, Object> clasificacion) {
		this.tipoClasificacionId = Long.valueOf(clasificacion.get("id").toString());
		this.clasificacion = clasificacion;
	}

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	public Map<String, Object> getProducto() {
		return producto;
	}

	public void setProducto(Map<String, Object> producto) {
		this.productoId=Long.valueOf(producto.get("id").toString());
		this.producto = producto;
	}

	public Long getSedeId() {
		return sedeId;
	}

	public void setSedeId(Long sedeId) {
		this.sedeId = sedeId;
	}

	public Map<String, Object> getSede() {
		return sede;
	}

	public void setSede(Map<String, Object> sede) {
		this.sedeId = Long.valueOf(sede.get("id").toString());
		this.sede = sede;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBuzonId() {
		return buzonId;
	}
	public void setBuzonId(Long buzonId) {
		this.buzonId = buzonId;
	}
	public String getRutaAutorizacion() {
		return rutaAutorizacion;
	}
	public void setRutaAutorizacion(String rutaAutorizacion) {
		this.rutaAutorizacion = rutaAutorizacion;
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
	public Set<Documento> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}
	public Map<String, Object> getBuzon() {		
		return buzon;
	}
	public void setBuzon(Map<String, Object> buzon) {
		this.buzonId = Long.valueOf(buzon.get("id").toString());
		this.buzon = buzon;
	}	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

}
