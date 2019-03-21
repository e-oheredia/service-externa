package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.entity.DocumentoGuia;

@Repository
public interface IDocumentoGuiaDao extends CrudRepository<DocumentoGuia, Long> {
	
	@Query("SELECT d FROM DocumentoGuia d WHERE d.guia.id =?1 AND d.documento.id=?2")
	public DocumentoGuia findByGuiaIdAndDocumentoId(Long guiaId, Long documentoId);
		
	@Query("SELECT dg FROM DocumentoGuia dg WHERE dg.guia.id = ?1 AND dg.validado = 0")
	public Iterable<DocumentoGuia> listarNoValidados(Long guiaId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM DocumentoGuia dg WHERE dg.documento.id = ?1")
	public void retirarDocumento(Long documentoId);
	
	@Query("SELECT d FROM DocumentoGuia d WHERE d.documento.id=?1")
	public DocumentoGuia findByDocumentoId(Long documentoId);
	
	@Query(value="select count(*) from documento_guia where guia_id=?",nativeQuery=true)
	public int getCantidadDocumentos(Long guiaId);
	
}
