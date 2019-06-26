package com.exact.service.externa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.entity.AmbitoPlazoDistribucion;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.PlazoDistribucion;

@Repository
public interface IAmbitoPlazoDistribucionDao extends CrudRepository<AmbitoPlazoDistribucion,Long>{

	
	@Query("FROM AmbitoPlazoDistribucion ap WHERE ap.plazoDistribucion.id=?1")
	public AmbitoPlazoDistribucion findAmbitoPlazo(Long plazoId);
	
	@Query("SELECT ap FROM AmbitoPlazoDistribucion ap WHERE ap.plazoDistribucion.id=?1")
	public Iterable<AmbitoPlazoDistribucion> listarAmbitosIds(Long plazoId);
	

	@Query("SELECT ap.plazoDistribucion FROM AmbitoPlazoDistribucion ap WHERE ap.id.ambitoId=?1")
	public Iterable<PlazoDistribucion> listarPlazosByAmbitoId(Long ambitoId);

	@Transactional
	@Modifying
	@Query("DELETE FROM AmbitoPlazoDistribucion ap WHERE ap.plazoDistribucion.id=?1")
	void eliminarbyproveedorid(Long plazoid);
	//ambitoplazos.getId().getAmbitoId()
	//@Query("SELECT ap FROM AmbitoPlazoDistribucion ap WHERE ap.ambitoId=?1 ")
	//public Iterable<AmbitoPlazoDistribucion> listarplazosIds(Long ambitoId);
	
	//@Query(value=" Select * From ambito_proveedor As ap where ap.ambito_id=?1")	
	//public Iterable<AmbitoPlazoDistribucion> listarplazosIds(Long ambitoId);

	
}
