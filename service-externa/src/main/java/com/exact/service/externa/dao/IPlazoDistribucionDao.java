package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.PlazoDistribucion;

@Repository
public interface IPlazoDistribucionDao extends CrudRepository<PlazoDistribucion, Long> {
	
	@Query("SELECT P.plazosDistribucion FROM Proveedor P WHERE P.id = ?1")
	Iterable<PlazoDistribucion> findByProveedorId(Long proveedorId);
	
}