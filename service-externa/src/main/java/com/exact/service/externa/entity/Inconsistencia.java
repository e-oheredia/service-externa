package com.exact.service.externa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "inconsistencia")
public class Inconsistencia implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "incosistencia_id")
	private Long id;
	
	@Column(name = "documento_autogenerado")
	private String documentoAutogenerado;
	@Column(name = "razon_social")
	private String razonSocial;
	private String contacto;
	private String departamento;
	private String provincia;
	private String distrito;
	private String direccion;
	private String referencia;
	private String telefono;
	@Column(name = "numero_documento")
	private String numeroDocumento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="envio_id")
	@JsonFilter("envioFilter")
	@JsonProperty("envio")
	private Envio envio;
	


	public Long getId() {
		return id;
	}

	

	public Envio getEnvio() {
		return envio;
	}



	public void setEnvio(Envio envio) {
		this.envio = envio;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getDocumentoAutogenerado() {
		return documentoAutogenerado;
	}



	public void setDocumentoAutogenerado(String documentoAutogenerado) {
		this.documentoAutogenerado = documentoAutogenerado;
	}


	public String getRazonSocial() {
		return razonSocial;
	}



	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}



	public String getContacto() {
		return contacto;
	}



	public void setContacto(String contacto) {
		this.contacto = contacto;
	}



	public String getNumeroDocumento() {
		return numeroDocumento;
	}



	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}



	public String getDepartamento() {
		return departamento;
	}



	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}



	public String getProvincia() {
		return provincia;
	}



	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}



	public String getDistrito() {
		return distrito;
	}



	public void setDistrito(String distrito) {
		this.distrito = distrito;
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



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
