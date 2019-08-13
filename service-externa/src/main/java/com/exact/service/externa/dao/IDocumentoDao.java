package com.exact.service.externa.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.TipoDevolucion;


@Repository
public interface IDocumentoDao extends CrudRepository<Documento, Long> {
	
	@Query(value="SELECT TOP 1 documento_autogenerado FROM documento ORDER BY documento_id DESC", nativeQuery=true)
	public String getMaxDocumentoAutogenerado();
	
	@Query("FROM Documento d WHERE d.envio IN (SELECT e FROM Envio e WHERE "
			+ "(e.plazoDistribucion.id = ?1 AND e.tipoServicio.id =?2 AND e.productoId =?3 AND e.tipoClasificacionId=?4 AND e.tipoSeguridad.id = ?5 AND e.sedeId=?6 AND e.tipoEnvio.id=1 AND e.seguimientosAutorizado IS EMPTY) OR "
			+ "(e.plazoDistribucion.id = ?1 AND e.tipoServicio.id =?2 AND e.productoId =?3 AND e.tipoClasificacionId=?4 AND e.tipoSeguridad.id = ?5 AND e.sedeId=?6 AND e.tipoEnvio.id=1 "
			+ "AND e IN (SELECT sa.envio FROM SeguimientoAutorizado sa WHERE sa.id = (SELECT MAX(sa2.id) FROM SeguimientoAutorizado sa2 WHERE sa2.envio.id=e.id) AND sa.estadoAutorizado.id=2 ))) AND "
			+ "d IN (SELECT sd.documento FROM SeguimientoDocumento sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 "
			+ "WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = 2) AND "
			+ "d.id NOT IN (SELECT dg.documento.id FROM DocumentoGuia dg)" )
	public Iterable<Documento> findByPlazoDistribucionAndTipoServicioAndTipoSeguridadAndProductoAndClasificacion(Long plazoDistribucionId, Long tipoServicioId,Long producto, Long clasificacion, Long tipoSeguridadId, Long sedeId);	
	
	
	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd " 
			+ "WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND " 
			+ "sd.estadoDocumento.id = ?1)")
	public Iterable<Documento> listarDocumentosPorEstado(Long estadoDocumentoId);
	
	
	@Query("FROM Documento d WHERE d.envio.buzonId=?3 AND d IN (SELECT sd.documento FROM SeguimientoDocumento sd "
			+ "WHERE cast(sd.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND sd.estadoDocumento.id=1)")
	public Iterable<Documento> listarReporteBCP(Date fechaIni, Date fechaFin, Long idbuzon);
	

	public Iterable<Documento> findAllByDocumentoAutogeneradoIn(List<String> autogeneradoList);

	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd " 
			+ "WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND " 
			+ "sd.estadoDocumento.id =4) AND d.codigoDevolucion='' AND d.envio.sedeId=?1 ")
	public Iterable<Documento> listarDocumentosEntregados(Long sedeId);
	
	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd " 
			+ "WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND " 
			+ "(sd.estadoDocumento.id =5 OR sd.estadoDocumento.id =6)) AND d.codigoDevolucion='' AND d.envio.sedeId=?1")
	public Iterable<Documento> listarDocumentosDevueltos(Long sedeId);

	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd "
			+ "WHERE cast(sd.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND sd.estadoDocumento.id=1) AND d.envio IN (SELECT e FROM Envio e WHERE e.tipoEnvio.id=1)")
	public Iterable<Documento> listarReporteUTD(Date fechaIni, Date fechaFin);
	
	
	@Query("FROM Documento d WHERE d.documentoAutogenerado=?1 AND d.envio IN (SELECT e FROM Envio e WHERE e.tipoEnvio.id=1)")
	public Documento listarDocumento(String autogenerado);
	
	
	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd "
			+ "WHERE cast(sd.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND sd.estadoDocumento.id=?3)")
	public Iterable<Documento> listarDocumentosParaVolumen(Date fechaini, Date fechafin, Long estadoDocumentoId);
	
	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd " 
			+ "WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND "
			+ "cast(sd.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND sd.estadoDocumento.id =4)")
	public Iterable<Documento> listarCargos(Date fechaini, Date fechafin);
	
	@Query("FROM Documento d WHERE d.envio.id=?1 AND d.envio IN (SELECT e FROM Envio e WHERE "
			+ "e.sedeId=?2) AND d IN (SELECT sd.documento FROM SeguimientoDocumento sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 "
			+ "WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = 1) AND "
			+ "d.id NOT IN (SELECT dg.documento.id FROM DocumentoGuia dg)" )
	public Iterable<Documento> findDocumentosByEnvioId(Long envioId, Long sedeId);
	
	
	@Query("SELECT d FROM Documento d WHERE d IN (SELECT dg.documento FROM DocumentoGuia dg WHERE dg.guia.id=?1)")
	public Iterable<Documento> findDocumentosByGuiaId(Long guiaId);


	@Query("FROM Documento d WHERE   d.envio.sedeId=?1 AND d IN (SELECT sd.documento FROM SeguimientoDocumento sd " 
			+ "WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND " 
			+ "(sd.estadoDocumento.id =4 OR sd.estadoDocumento.id =5 OR (sd.estadoDocumento.id =6 AND sd.motivoEstado.id=16) OR sd.estadoDocumento.id =9)) AND d.envio.tipoEnvio.id=1  ")
	public Iterable<Documento> listarDocumentosParaRecepcionar(Long sedeId);
	
	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd"
			+ " WHERE sd.id =(SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id=d.id) AND "
			+ "(sd.estadoDocumento.id =4 OR sd.estadoDocumento.id =5 OR sd.estadoDocumento.id =6)) AND d.id=?1 AND d.envio.sedeId=?2")
	public Documento findDocumentoaRecepcionar(Long documentoId, Long sedeId);
	
	
	@Query("SELECT d.tiposDevolucion FROM Documento d WHERE d.id=?1")
	public Iterable<TipoDevolucion> findTiposDevolucionByDocumentoId(Long documentoId);


	@Query("FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd " 
			+ "WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND " 
			+ "(sd.estadoDocumento.id!=4 AND sd.estadoDocumento.id!=5 AND sd.estadoDocumento.id!=6 AND sd.estadoDocumento.id!=9 AND sd.estadoDocumento.id!=10)) AND "
			+ "d IN (SELECT dg.documento FROM DocumentoGuia dg WHERE dg.guia.id=?1)")
	public Iterable<Documento> listardocumentosPendientes(Long guiaId);
	
	
	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Documento d WHERE d IN (SELECT dg.documento FROM DocumentoGuia dg WHERE"
			+ " dg.guia.id=?1) AND d IN (SELECT sd.documento FROM SeguimientoDocumento sd WHERE sd.id=("
			+ " SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = 3)")
	boolean existeDocumentosPendientes(Long id);

	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd " 
	+ "WHERE sd.motivoEstado.id=16) AND d.id=?1")
	boolean findDocumentoConDenuncias(Long documentosId);

}
