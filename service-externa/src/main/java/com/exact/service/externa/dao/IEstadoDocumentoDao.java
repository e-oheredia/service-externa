package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.SeguimientoDocumento;

@Repository
public interface IEstadoDocumentoDao extends CrudRepository<EstadoDocumento,Long>{

	@Query("FROM EstadoDocumento e WHERE e.tipoEstadoDocumento.id= ?1 ")
	public Iterable<EstadoDocumento> findByTipoEstadoDocumentoId(Long tipoEstadoDocumentoId);
	
	
	@Query("SELECT SG.estadoDocumento FROM SeguimientoDocumento SG where SG.id=(Select MAX(sg.id) FROM SeguimientoDocumento sg WHERE sg.documento.id = ?1)")
	public EstadoDocumento buscarpordocumento(Long id);
	
	
}
