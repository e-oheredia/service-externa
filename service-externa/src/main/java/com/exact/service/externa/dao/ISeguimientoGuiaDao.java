package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.entity.SeguimientoGuia;

@Repository
public interface ISeguimientoGuiaDao extends CrudRepository<SeguimientoGuia, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM SeguimientoGuia sg WHERE sg.guia.id = ?1")
	public void retirarSeguimiento(Long guiaId);
	
}
