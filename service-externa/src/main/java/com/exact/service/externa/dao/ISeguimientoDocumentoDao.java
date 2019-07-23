package com.exact.service.externa.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.SeguimientoDocumento;

@Repository
public interface ISeguimientoDocumentoDao extends CrudRepository<SeguimientoDocumento, Long> {
	
		/*
	@Query("SELECT SG FROM seguimientoDocumento SG where SG.id=(Select MAX(sg.id) FROM SeguimientoDocumento sg WHERE sg.documento.id = ?1)")
	public SeguimientoDocumento buscarpordocumento(Long id);
	*/
	
	@Query(value="SELECT SG.fecha from seguimiento_documento SG WHERE SG.documento_id=?1 and (SG.estado_documento_id=4 or SG.estado_documento_id=5)", nativeQuery=true)
	public Date buscarpordocumento(Long id);
	
	@Query("SELECT SG FROM SeguimientoDocumento SG where SG.id=(Select MAX(sg.id) FROM SeguimientoDocumento sg WHERE sg.documento.id = ?1) and SG.documento.id=?1 ")
	public    SeguimientoDocumento  segmax(Long documentoid);
	
	/*@Query("SELECT sd.fecha FROM SeguimientoDocumento sd"
			+ " WHERE sd.id =(SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id=?1) AND "
			+ "(sd.estadoDocumento.id = 4 OR sd.estadoDocumento.id = 5)" )
	public Date buscarpordocumento2(Long documentoId);*/
}
