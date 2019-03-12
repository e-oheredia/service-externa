package com.exact.service.externa.dao;

import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.entity.ProveedorPlazoDistribucion;

public interface IProveedorPlazoDistribucionDao extends CrudRepository<ProveedorPlazoDistribucion, Long>{

	@Query(value="SELECT plazo_id from proveedor_plazo_distribucion where proveedor_id = ?1" ,nativeQuery=true)
	public Iterable<Map<String, Object>> getPlazoDistribucionIdByProveedorId(Long id);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ProveedorPlazoDistribucion pp WHERE pp.proveedorId = ?1")
	public void eliminarPlazos(Long id);
}