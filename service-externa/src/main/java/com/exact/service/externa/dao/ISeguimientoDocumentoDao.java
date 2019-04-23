package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.SeguimientoDocumento;

@Repository
public interface ISeguimientoDocumentoDao extends CrudRepository<SeguimientoDocumento, Long> {
/*
	@Query("SELECT SG FROM seguimientoDocumento SG where SG.id=(Select MAX(sg.id) FROM SeguimientoDocumento sg WHERE sg.documento.id = ?1)")
	public SeguimientoDocumento buscarpordocumento(Long id);
	*/
}
