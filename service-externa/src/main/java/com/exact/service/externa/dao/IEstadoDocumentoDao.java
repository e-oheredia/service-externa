package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.EstadoDocumento;

@Repository
public interface IEstadoDocumentoDao extends CrudRepository<EstadoDocumento,Long>{

	@Query("FROM EstadoDocumento e WHERE e.tipoEstadoDocumento.id= ?1 ")
	public Iterable<EstadoDocumento> findByTipoEstadoDocumentoId(Long tipoEstadoDocumentoId);
	

}
