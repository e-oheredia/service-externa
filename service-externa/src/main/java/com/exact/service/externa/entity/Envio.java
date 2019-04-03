package com.exact.service.externa.entity;

import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="envio")
@Inheritance(
	    strategy = InheritanceType.JOINED
	)
public class Envio implements Serializable {	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="envio_id")
	private Long id;
	@Column(name="buzon_id")
	private Long buzonId;
	@Column(name="ruta_autorizacion")
	private String rutaAutorizacion;
	private boolean autorizado;
	@Column(name="tipo_documento_id")
	private Long tipoDocumentoId;
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
	private Map<String, Object> tipoDocumento;			
	@Transient
	private Map<String, Object> buzon;
	@Transient
	private Map<String, Object> sede;
	@Transient
	private Map<String, Object> producto;
	
		
	
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

	@PrePersist
	public void prePersist() {
		if (rutaAutorizacion == null || rutaAutorizacion.isEmpty()) {
			this.autorizado = true;
		}
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
	public boolean isAutorizado() {
		return autorizado;
	}
	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}
	public Long getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(Long tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
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
	public Map<String, Object> getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(Map<String, Object> tipoDocumento) {
		this.tipoDocumentoId = Long.valueOf(tipoDocumento.get("id").toString());
		this.tipoDocumento = tipoDocumento;
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
