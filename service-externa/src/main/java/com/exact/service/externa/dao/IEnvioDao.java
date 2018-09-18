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
}
