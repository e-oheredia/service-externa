package com.exact.service.externa.dao;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Inconsistencia;

@Repository
public interface IInconsistenciaDao extends CrudRepository<Inconsistencia, Long>{

	@Query("FROM Inconsistencia i WHERE i IN (SELECT e.inconsistencias FROM Envio e WHERE e.id=?1)")
	public Iterable<Inconsistencia> findInconsistenciasByEnvioId(Long envioId);
	
}