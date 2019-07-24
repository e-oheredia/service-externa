package com.exact.service.externa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.EstadoDocumento;

@Repository
public interface IEstadoDocumentoDao extends CrudRepository<EstadoDocumento,Long>{

	@Query("FROM EstadoDocumento e WHERE e.tipoEstadoDocumento.id= ?1 ")
	public Iterable<EstadoDocumento> findByTipoEstadoDocumentoId(Long tipoEstadoDocumentoId);
	
	
	@Query("SELECT SG.estadoDocumento FROM SeguimientoDocumento SG where SG.id=(Select MAX(sg2.id) FROM SeguimientoDocumento sg2 WHERE sg2.documento.id = ?1)")
	public EstadoDocumento buscarpordocumento(Long id);
	
	@Query("SELECT SG.estadoDocumento.id FROM SeguimientoDocumento SG where SG.id=(Select MAX(sg2.id) FROM SeguimientoDocumento sg2 WHERE sg2.documento.id = ?1)")
	public Iterable<Long> findEstadoDocumentoIds(List<Long> ids);
	
}
