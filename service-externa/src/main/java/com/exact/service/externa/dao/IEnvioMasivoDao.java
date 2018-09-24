package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.exact.service.externa.entity.EnvioMasivo;

public interface IEnvioMasivoDao extends CrudRepository<EnvioMasivo, Long> {

	@Query(value="SELECT TOP 1 masivo_autogenerado FROM envio_masivo ORDER BY envio_id DESC ", nativeQuery=true)
	public String getMaxMasivoAutogenerado();
	
	@Query("FROM Envio e WHERE e.masivoAutogenerado IS NOT NULL AND e IN (SELECT d.envio FROM Documento d WHERE d IN (SELECT sd.documento "
			+ "FROM SeguimientoDocumento sd WHERE sd.id = (SELECT MAX(sd2.id) FROM SeguimientoDocumento sd2 "
			+ "WHERE sd2.documento.id = d.id) AND sd.estadoDocumento.id = ?1))")
	public Iterable<EnvioMasivo> findByUltimoEstadoId(Long ultimoEstadoId);
}
