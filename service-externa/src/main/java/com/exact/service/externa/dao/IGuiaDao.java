package com.exact.service.externa.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.Guia;

@Repository
public interface IGuiaDao extends CrudRepository<Guia,Long>{

	@Query("FROM Guia d WHERE d IN (SELECT sd.guia "
			+ "FROM SeguimientoGuia sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoGuia sd2 "
			+ "WHERE sd2.guia.id = d.id) AND sd.estadoGuia.id = ?1) AND d.sedeId=?2")
	public Iterable<Guia> findByUltimoEstadoId(Long ultimoEstadoId, Long sedeId);
	
	@Query("FROM Guia d WHERE d IN (SELECT sd.guia "
			+ "FROM SeguimientoGuia sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoGuia sd2 "
			+ "WHERE sd2.guia.id = d.id AND cast(sd2.fecha as date) BETWEEN  DATEADD(DAY,-7,cast(GETDATE() as date)) AND cast(GETDATE() as date) ) AND sd.estadoGuia.id > 1 ) ORDER BY d.id ASC")
	public Iterable<Guia> findByGuiasParaProveedor();
		
	@Query("FROM Guia d WHERE d IN ( "
			+ "SELECT sg.guia FROM SeguimientoGuia sg WHERE sg.id = ("
			+ "SELECT MAX(sg2.id) FROM SeguimientoGuia sg2 WHERE sg2.guia.id = d.id) AND sg.estadoGuia.id > 1 ) AND d IN ("
			
			+ "SELECT dg.guia FROM DocumentoGuia dg WHERE dg.documento IN ( "
			+ "SELECT do FROM Documento do WHERE do IN ("
			+ "SELECT sd.documento FROM SeguimientoDocumento sd WHERE sd.id = ("
			+ "SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = do.id) AND sd.estadoDocumento.id = 3))) "
			+ "ORDER BY d.id ASC")
	public Iterable<Guia> findByGuiasSinCerrar();
	
	@Query("FROM Guia d WHERE d IN ( "
			+ "SELECT sg.guia FROM SeguimientoGuia sg WHERE sg.id = (SELECT MAX(sg2.id) FROM SeguimientoGuia sg2 WHERE sg2.guia.id = d.id) AND sg.estadoGuia.id=1  AND sg.usuarioId=?1) AND d.tipoGuia.id=2 "
			+ "ORDER BY d.id ASC ")
	public Iterable<Guia> findByGuiasBloques(Long usuarioId);	
	
	
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Guia g WHERE g.id = ?1")
	public void retirarGuia(Long guiaId);
	
	@Query("FROM Guia g WHERE g.numeroGuia=?1")
	public Guia findBynumeroGuia(String numeroGuia);
	
	@Query("FROM Guia g WHERE g IN (SELECT sg.guia FROM SeguimientoGuia sg "
			+ "WHERE cast(sg.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date) AND sg.estadoGuia.id=1)")
	public Iterable<Guia> listarGuiasPorFechas(Date fechaIni, Date fechaFin);
	
	@Query("FROM Guia g WHERE g IN ( SELECT dg.guia FROM DocumentoGuia dg WHERE dg.documento IN ("
			+ "SELECT do FROM Documento do WHERE do.documentoAutogenerado=?1))")
	public Guia findGuiabyAutogenerado(String autogenereado);
	
	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Documento d WHERE d IN (SELECT dg.documento FROM DocumentoGuia dg WHERE"
			+ " dg.guia.id=?1) AND d IN (SELECT sd.documento FROM SeguimientoDocumento sd WHERE sd.id=("
			+ " SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = 3)")
	boolean existeDocumentosPendientes(Long id);
	
	@Query("FROM Documento d WHERE d IN (SELECT dg.documento FROM DocumentoGuia dg WHERE dg.guia.id=?1)")
	public Iterable<Documento> listarDocumentosByGuiaId(Long id);
	
	@Query("FROM Guia d WHERE d IN ( "
			+ "SELECT sg.guia FROM SeguimientoGuia sg WHERE sg.id = ("
			+ "SELECT MAX(sg2.id) FROM SeguimientoGuia sg2 WHERE sg2.guia.id = d.id) AND sg.estadoGuia.id=4) AND d.tipoGuia.id=2")
	public Iterable<Guia> listarGuiasCompletadas();	

	@Query("FROM Guia g WHERE g.numeroGuia=?1 AND g IN (SELECT sg.guia FROM SeguimientoGuia sg WHERE sg.id = ("
			+ "SELECT MAX (sg2.id) FROM SeguimientoGuia sg2 WHERE sg2.guia.id = g.id) AND (sg.estadoGuia.id=2 OR sg.estadoGuia.id=3))")
	public Guia findBynumeroGuiaActiva(String numeroGuia);
	
	@Query("FROM Guia g WHERE g IN (SELECT sg.guia FROM SeguimientoGuia sg "
			+ "WHERE sg.id = (SELECT MAX (sg2.id) FROM SeguimientoGuia sg2 WHERE sg2.guia.id=g.id) AND (cast(sg.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date)) AND (sg.estadoGuia.id=2 OR sg.estadoGuia.id=3))")
	public Iterable<Guia> listarGuiasActivasPorFechas(Date fechaIni, Date fechaFin);
	
	@Query("FROM Guia g WHERE g IN ( SELECT dg.guia FROM DocumentoGuia dg WHERE dg.documento IN ("
			+ "SELECT do FROM Documento do WHERE do.id=?1))")
	public Guia findGuiabydocumentoid(Long documentoid);
	
	
}
