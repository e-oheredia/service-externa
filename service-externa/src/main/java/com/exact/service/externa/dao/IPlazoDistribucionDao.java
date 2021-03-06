package com.exact.service.externa.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.PlazoDistribucion;

@Repository
public interface IPlazoDistribucionDao extends CrudRepository<PlazoDistribucion, Long> {
	
	/*@Query("SELECT P.plazosDistribucion FROM Proveedor P WHERE P.id = ?1")
	Iterable<PlazoDistribucion> findByProveedorId(Long proveedorId);*/
	
	@Query(value=" Select * From plazo_distribucion As p where p.plazo_distribucion_id	in (select pd.plazo_distribucion_id From proveedor_plazo_distribucion as pd where pd.proveedor_id=?1) and p.activo=1 ", nativeQuery=true)	
	Iterable<PlazoDistribucion> listarplazosactivos(Long proveedorId);
	

	/*@Query("FROM PlazoDistribucion P WHERE P.regionId = ?1")
	Iterable<PlazoDistribucion> findPlazosByRegionId(Long regionId);

	@Query("SELECT P FROM PlazoDistribucion P WHERE P.regionId = ?1")
	Iterable<PlazoDistribucion> plazosByRegion(Long Region);*/
	
	/*@Transactional
	@Modifying
	@Query(value="DELETE FROM proveedor_plazo_distribucion WHERE proveedor_plazo_distribucion.proveedor_id=?1", nativeQuery=true)
	void eliminarbyproveedorid(Long proveedorId);*/
	

	
	@Query("SELECT P FROM PlazoDistribucion P WHERE P.nombre = ?1")
	PlazoDistribucion buscarpornombre(String nombre);
	

}