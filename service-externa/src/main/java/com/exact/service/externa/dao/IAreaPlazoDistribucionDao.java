package com.exact.service.externa.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.AreaPlazoDistribucion;


@Repository
public interface IAreaPlazoDistribucionDao extends CrudRepository<AreaPlazoDistribucion, Long> {
	
	@Query("FROM AreaPlazoDistribucion ap WHERE ap.areaId=?1")
	public AreaPlazoDistribucion getPlazoDistribucionIdByAreaId(Long id);
	
	public Iterable<AreaPlazoDistribucion> findAllByAreaIdIn(List<Long> areaIds);
	
	@Query("FROM AreaPlazoDistribucion ap WHERE cast(ap.fechaAsociacion as date) BETWEEN cast(?1 as date) AND cast(?2 as date)")
	public Iterable<AreaPlazoDistribucion> listarPorFechasAreaPlazo(Date fechaIni, Date fechaFin);
}
