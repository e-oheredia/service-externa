package com.exact.service.externa.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Envio;

@Repository
public interface IEnvioDao extends CrudRepository<Envio, Long> {	


	@Query(value="SELECT MAX(envio_id) FROM envio", nativeQuery=true)
	public Long getMaxId();
	
	
	@Query("FROM Envio e WHERE e IN(SELECT sa.envio FROM SeguimientoAutorizado sa WHERE "
			+ "sa.id = (SELECT MAX(sa2.id) FROM SeguimientoAutorizado sa2 WHERE sa2.envio.id=e.id) AND sa.estadoAutorizado.id=?1)")
	public Iterable<Envio> findByEstadoAutorizado(Long estado);
	
	@Query("FROM Envio e WHERE e.masivoAutogenerado IS NULL AND e IN (SELECT d.envio FROM Documento d WHERE d IN (SELECT sd.documento "
			+ "FROM SeguimientoDocumento sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 "
			+ "WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = ?1)) AND e.sedeId=?2")
	public Iterable<Envio> findByUltimoEstadoId(Long ultimoEstadoId, Long sedeId);
	
	@Query("FROM Envio e WHERE e IN(SELECT sa.envio FROM SeguimientoAutorizado sa WHERE sa.id IS NOT NULL AND cast(sa.fecha as date) "
			+ "BETWEEN cast(?1 as date) AND cast(?2 as date))")
	public Iterable<Envio> listarEnviosAutorizacion(Date fechaIni, Date fechaFin);
	
	@Query("FROM Envio e WHERE e IN (SELECT sa.envio FROM SeguimientoAutorizado sa WHERE sa.id IS NOT NULL) AND e.id=?1")
	public Envio findEnvioConAutorizacion(Long id);
	
	@Query("FROM Envio e WHERE e IN (SELECT i.envio FROM Inconsistencia i WHERE i.id IS NOT NULL) AND e IN (SELECT d.envio FROM Documento d WHERE d IN (SELECT sd.documento FROM SeguimientoDocumento sd "
			+ "WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 WHERE sd2.documento.id=d.id) AND sd.estadoDocumento.id=1 "
			+ "AND cast(sd.fecha as date) BETWEEN cast(?1 as date) AND cast(?2 as date)))")
	public Iterable<Envio> listarEnviosInconsistencias(Date fechaIni, Date fechaFin);
	
}
