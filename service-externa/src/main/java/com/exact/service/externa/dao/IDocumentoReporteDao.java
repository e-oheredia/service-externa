package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.DocumentoReporte;

@Repository
public interface IDocumentoReporteDao extends CrudRepository<DocumentoReporte, Long>{

	@Query("SELECT dr FROM DocumentoReporte dr WHERE dr.documentoId=?1")
	public DocumentoReporte findByDocumentoId(Long documentoId);
	
}
