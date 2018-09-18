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
	private Set<Documento> documentos;
	@Transient
	private Map<String, Object> tipoDocumento;			
	@Transient
	private Map<String, Object> buzon;
	
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
