package com.exact.service.externa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.exact.service.externa.entity.id.ReporteAsignacionPlazoId;

@Entity
@Table(name="reporte_asignacion_plazo")
public class ReporteAsignacionPlazo{


	@EmbeddedId
	private ReporteAsignacionPlazoId id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("areaPlazoDistribucionId")
	@JoinColumn(name="area_id")
	private AreaPlazoDistribucion areaPlazoDistribucion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("buzonPlazoDistribucionId")
	@JoinColumn(name="buzon_id")
	private BuzonPlazoDistribucion buzonPlazoDistribucion;
	
	@Column(name="tipo_asignacion")
	private String tipoAsignacion;
	
	@Column(name="area_buzon")
	private String areaBuzon;
	
	@Column(name="plazo_actual")
	private String plazoActual;
	
	@Column(name="fecha")
	private Date fecha;
	
	@Column(name="ruta_autorizacion")
	private String rutaAutorizacion;

	public ReporteAsignacionPlazoId getId() {
		return id;
	}

	public void setId(ReporteAsignacionPlazoId id) {
		this.id = id;
	}

	public AreaPlazoDistribucion getAreaPlazoDistribucion() {
		return areaPlazoDistribucion;
	}

	public void setAreaPlazoDistribucion(AreaPlazoDistribucion areaPlazoDistribucion) {
		this.areaPlazoDistribucion = areaPlazoDistribucion;
	}

	public BuzonPlazoDistribucion getBuzonPlazoDistribucion() {
		return buzonPlazoDistribucion;
	}

	public void setBuzonPlazoDistribucion(BuzonPlazoDistribucion buzonPlazoDistribucion) {
		this.buzonPlazoDistribucion = buzonPlazoDistribucion;
	}

	

	public String getTipoAsignacion() {
		return tipoAsignacion;
	}

	public void setTipoAsignacion(String tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
	}

	public String getAreaBuzon() {
		return areaBuzon;
	}

	public void setAreaBuzon(String areaBuzon) {
		this.areaBuzon = areaBuzon;
	}

	

	public String getPlazoActual() {
		return plazoActual;
	}

	public void setPlazoActual(String plazoActual) {
		this.plazoActual = plazoActual;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getRutaAutorizacion() {
		return rutaAutorizacion;
	}

	public void setRutaAutorizacion(String rutaAutorizacion) {
		this.rutaAutorizacion = rutaAutorizacion;
	}
	


	
}
