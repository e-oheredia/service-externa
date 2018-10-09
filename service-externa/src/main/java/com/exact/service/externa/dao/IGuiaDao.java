package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Guia;

@Repository
public interface IGuiaDao extends CrudRepository<Guia,Long>{

	@Query("FROM Guia d WHERE d IN (SELECT sd.guia "
			+ "FROM SeguimientoGuia sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoGuia sd2 "
			+ "WHERE sd2.guia.id = d.id) AND sd.estadoGuia.id = ?1)")
	public Iterable<Guia> findByUltimoEstadoId(Long ultimoEstadoId);
		
}
