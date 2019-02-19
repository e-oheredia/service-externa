package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Envio;

@Repository
public interface IEnvioDao extends CrudRepository<Envio, Long> {	


	@Query(value="SELECT MAX(envio_id) FROM envio", nativeQuery=true)
	public Long getMaxId();
	
	
	public Iterable<Envio> findByAutorizado(boolean autorizado);
	
	@Query("FROM Envio e WHERE e.masivoAutogenerado IS NULL AND e IN (SELECT d.envio FROM Documento d WHERE d IN (SELECT sd.documento "
			+ "FROM SeguimientoDocumento sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 "
			+ "WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = ?1)) AND e.sedeId=?2")
	public Iterable<Envio> findByUltimoEstadoId(Long ultimoEstadoId, Long sedeId);
	
}
