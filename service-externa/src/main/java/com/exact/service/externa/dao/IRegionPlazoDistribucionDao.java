package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.RegionPlazoDistribucion;

@Repository
public interface IRegionPlazoDistribucionDao extends CrudRepository<RegionPlazoDistribucion, Long>{

	@Query(value="SELECT * FROM region_plazo_distribucion WHERE region_id=?1 AND plazo_distribucion_id=?2", nativeQuery=true)
	public RegionPlazoDistribucion getPlazoDistribucionBySubambitoId(Long id, Long plazoId);
	
}
