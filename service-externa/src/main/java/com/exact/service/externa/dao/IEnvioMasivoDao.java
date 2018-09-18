package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.exact.service.externa.entity.EnvioMasivo;

public interface IEnvioMasivoDao extends CrudRepository<EnvioMasivo, Long> {

	@Query(value="SELECT TOP 1 masivo_autogenerado FROM envio_masivo ORDER BY envio_id DESC ", nativeQuery=true)
	public String getMaxMasivoAutogenerado();
}
