package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.exact.service.externa.entity.AmbitoProveedor;

public interface IAmbitoProveedorDao extends CrudRepository<AmbitoProveedor,Long >{

	
	@Query("SELECT ap FROM AmbitoProveedor ap WHERE ap.proveedores.id=?1")
	public Iterable<AmbitoProveedor> listarAmbitosIds(Long proveedorId);
	
	
}
