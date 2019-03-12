package com.exact.service.externa.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="proveedor_plazo_distribucion")
public class ProveedorPlazoDistribucion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="proveedor_plazo_distribucion_id")
	private Long id;
	@Column(name="proveedor_id")
	private Long proveedorId;
	@Column(name="plazo_id")
	private Long plazoId;
	@Transient
	private Map<String, Object> proveedor;
	@Transient
	private Iterable<Map<String, Object>> plazos;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}
	public Long getPlazoId() {
		return plazoId;
	}
	public void setPlazoId(Long plazoId) {
		this.plazoId = plazoId;
	}
	public Map<String, Object> getProveedor() {
		return proveedor;
	}
	public void setProveedor(Map<String, Object> proveedor) {
		this.proveedorId=Long.valueOf(proveedor.get("id").toString());
		this.proveedor = proveedor;
	}
	public Iterable<Map<String, Object>> getPlazos() {
		return plazos;
	}
	public void setPlazos(Iterable<Map<String, Object>> plazos) {
		List<Map<String, Object>> plazoslst = StreamSupport.stream(plazos.spliterator(), false).collect(Collectors.toList());
		for(int i=0;i<plazoslst.size();i++) {
			this.plazoId=Long.valueOf(plazoslst.get(i).toString());
		}
		this.plazos = plazos;
	}

	
	
	
}
