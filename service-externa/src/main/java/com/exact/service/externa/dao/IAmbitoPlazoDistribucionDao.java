package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.AmbitoPlazoDistribucion;
import com.exact.service.externa.entity.Documento;

@Repository
public interface IAmbitoPlazoDistribucionDao extends CrudRepository<AmbitoPlazoDistribucion,Long>{

	
	@Query("FROM AmbitoPlazoDistribucion ap WHERE ap.plazoDistribucion.id=?1")
	public AmbitoPlazoDistribucion findAmbitoPlazo(Long plazoId);
	
}
