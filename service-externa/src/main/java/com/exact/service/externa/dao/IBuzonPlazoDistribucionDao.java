package com.exact.service.externa.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.exact.service.externa.entity.BuzonPlazoDistribucion;

@Repository
public interface IBuzonPlazoDistribucionDao extends CrudRepository<BuzonPlazoDistribucion, Long> {
	//value="SELECT plazo_distribucion_id from buzon_plazo_distribucion where buzon_id = ?1", nativeQuery=true
	@Query("FROM BuzonPlazoDistribucion bp WHERE bp.buzonId=?1")
	public BuzonPlazoDistribucion getPlazoDistribucionIdByBuzonId(Long id);
	
	public Iterable<BuzonPlazoDistribucion> findAllByBuzonIdIn(List<Long> buzonIds);
	
	@Query("FROM BuzonPlazoDistribucion bp WHERE cast(bp.fechaAsociacion as date) BETWEEN cast(?1 as date) AND cast(?2 as date)")
	public Iterable<BuzonPlazoDistribucion> listarPorFechasBuzonPlazo(Date fechaIni, Date fechaFin);
	
}
