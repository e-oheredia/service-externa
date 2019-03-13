package com.exact.service.externa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="plazo_distribucion")
public class PlazoDistribucion implements Serializable{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="plazo_distribucion_id")
	private Long id;
	@Column(nullable=false)
	private String nombre;
	
	@Column(name="tiempo_envio", nullable=false)
	private int tiempoEnvio;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="tipo_plazo_distribucion_id")
	private TipoPlazoDistribucion tipoPlazoDistribucion;
	
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}