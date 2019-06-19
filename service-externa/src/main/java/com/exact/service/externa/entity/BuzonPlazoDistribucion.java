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


@Entity
@Table(name="buzon_plazo_distribucion")
public class BuzonPlazoDistribucion implements Serializable {
	
	
	
	@Id
	@Column(name="buzon_id")
	private Long buzonId;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="plazo_distribucion_id")
	private PlazoDistribucion plazoDistribucion;
	@Transient
	private Map<String, Object> buzon;	
	@Column(name="ruta_autorizacion")
	private String rutaAutorizacion;
	
	@Column(name="fecha_asociacion")
	private Date fechaAsociacion;
	
	@PrePersist
	public void prePersist() {
		fechaAsociacion = new Date();
	}
	
	
	
	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}



	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
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

	public Long getBuzonId() {
		return buzonId;
	}

	public void setBuzonId(Long buzonId) {
		this.buzonId = buzonId;
	}

	public Map<String, Object> getBuzon() {
		return buzon;
	}

	public void setBuzon(Map<String, Object> buzon) {
		this.buzonId=Long.valueOf(buzon.get("id").toString());
		this.buzon = buzon;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
