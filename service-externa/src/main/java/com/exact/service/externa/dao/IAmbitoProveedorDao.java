package com.exact.service.externa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.entity.AmbitoProveedor;
import com.exact.service.externa.entity.PlazoDistribucion;

public interface IAmbitoProveedorDao extends CrudRepository<AmbitoProveedor,Long >{

	
	@Query("SELECT ap FROM AmbitoProveedor ap WHERE ap.proveedores.id=?1")
	public Iterable<AmbitoProveedor> listarAmbitosIds(Long proveedorId);
	
	
	@Transactional
	@Modifying
	@Query("DELETE FROM AmbitoProveedor ap WHERE ap.proveedores.id=?1")
	void eliminarbyproveedorid(Long proveedorId);	
	
	
	@Query(value=" Select * From ambito_proveedor As ap where ap.ambito_id=?1 ", nativeQuery=true)	
	public Iterable<AmbitoProveedor> findProveedorByAmbitoId(Long ambitoId);
	
}
