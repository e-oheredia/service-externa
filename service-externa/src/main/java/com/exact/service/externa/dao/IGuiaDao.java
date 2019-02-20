package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//import java.time.LocalDate;

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
			+ "SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id = do.id) AND sd.estadoDocumento.id = 3)))"
			+ "ORDER BY d.id ASC")
	public Iterable<Guia> findByGuiasSinCerrar();
}
