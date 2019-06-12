package com.exact.service.externa.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.DocumentoReporte;

@Repository
public interface IDocumentoReporteDao extends CrudRepository<DocumentoReporte, Long>{

	@Query("SELECT dr FROM DocumentoReporte dr WHERE dr.documentoId=?1")
	public DocumentoReporte findByDocumentoId(Long documentoId);
	
	@Query("SELECT dr FROM DocumentoReporte dr WHERE cast(dr.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND (dr.tiempoEntrega=2 OR dr.tiempoEntrega=3) AND dr.estadoDocumento=4")
	public Iterable<DocumentoReporte> findDocumentosByDentroFueraPlazo(Date fechaIni, Date fechaFin);

	@Query("FROM DocumentoReporte dr WHERE cast(dr.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date)")
	public Iterable<DocumentoReporte> buscarvolumenporfechas(Date fechaIni, Date fechaFin);
	
	
	@Query("FROM DocumentoReporte dr WHERE MONTH(dr.fecha) = ?1 AND YEAR(dr.fecha) = ?2")
	public Iterable<DocumentoReporte> buscarvolumenporfechas2(int mes, int año);
	

	@Query("SELECT dr FROM DocumentoReporte dr WHERE cast(dr.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND (dr.tiempoEntrega=2 OR dr.tiempoEntrega=3) AND dr.estadoDocumento=4 AND dr.proveedorId=?3")
	public Iterable<DocumentoReporte> findDocumentosByProveedorId(Date fechaIni, Date fechaFin, Long proveedorId);
	
	@Query("SELECT dr FROM DocumentoReporte dr WHERE cast(dr.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND (dr.estadoCargo=2 OR dr.estadoCargo=3) ")
	public Iterable<DocumentoReporte> findDocumentosByEstadoCargo(Date fechaIni, Date fechaFin);

}
