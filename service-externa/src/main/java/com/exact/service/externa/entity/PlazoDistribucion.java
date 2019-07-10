package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="plazo_distribucion")
public class PlazoDistribucion implements Serializable{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="plazo_distribucion_id")
	private Long id;
	@Column(nullable=false, unique=true)
	private String nombre;
	
	@Column(name="tiempo_envio", nullable=false)
	private int tiempoEnvio;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="tipo_plazo_distribucion_id")
	private TipoPlazoDistribucion tipoPlazoDistribucion;
	

	/*@Column(name="region_id", nullable = true)
	private Long regionId;
	
	@Transient
	private Map<String, Object> region;*/
	
	@Transient
	private Set<Map<String, Object>> regiones;
	
	

/*
	public Long getRegionId() {
		return regionId;
	}
	
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	
	*/
	
	
	/*public Map<String, Object> getRegion() {
		return region;
	}
	
	
	public void setRegion(Map<String, Object> region) {
		if(region !=null) {
			this.regionId=Long.valueOf(region.get("id").toString());
			this.region = region;
		}
	}*/
	

//	@Transient
//	private Set<Map<String, Object>> ambitos;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "plazoDistribucion")
//	private Set<AmbitoPlazoDistribucion> ambitosPlazoDistribucion;
	
	
//	public Set<Map<String, Object>> getAmbitos() {
//		return ambitos;
//	}
//	public void setAmbitos(Set<Map<String, Object>> ambitos) {
//		this.ambitos = ambitos;
//		this.ambitosPlazoDistribucion = new HashSet<AmbitoPlazoDistribucion>();
//		this.ambitos.forEach(ambito -> {
//			AmbitoPlazoDistribucion ambitoPlazo = new AmbitoPlazoDistribucion();
//			ambitoPlazo.setAmbito(Long.valueOf(ambito.get("id").toString()));
//			this.ambitosPlazoDistribucion.add(ambitoPlazo);
//		});
//		
//		
//	}



	public Set<Map<String, Object>> getRegiones() {
		return regiones;
	}
	public void setRegiones(Set<Map<String, Object>> regiones) {
		this.regiones = regiones;
	}


	@Column(name="activo", nullable=false)
	private boolean activo;
	

	/*
	public Set<Map<String, Object>> getAmbitos() {
		return ambitos;
	}
	public void setAmbitos(Set<Map<String, Object>> ambitos) {
		this.ambitos = ambitos;
	}
	*/
	
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getTiempoEnvio() {
		return tiempoEnvio;
	}
	public void setTiempoEnvio(int tiempoEnvio) {
		this.tiempoEnvio = tiempoEnvio;
	}
	public TipoPlazoDistribucion getTipoPlazoDistribucion() {
		return tipoPlazoDistribucion;
	}
	public void setTipoPlazoDistribucion(TipoPlazoDistribucion tipoPlazoDistribucion) {
		this.tipoPlazoDistribucion = tipoPlazoDistribucion;
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

	
	private static final long serialVersionUID = 1L;
}
