package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.DocumentoGuia;

@Repository
public interface IDocumentoGuiaDao extends CrudRepository<DocumentoGuia, Long> {
	
	@Query("SELECT d FROM DocumentoGuia d WHERE d.guia.id =?1 AND d.documento.id=?2")
	public DocumentoGuia findByGuiaIdAndDocumentoId(Long guiaId, Long documentoId);
		
	@Query("SELECT dg FROM DocumentoGuia dg WHERE dg.guia.id = ?1 AND dg.validado = 0")
	public Iterable<DocumentoGuia> listarNoValidados(Long guiaId);
	
}
