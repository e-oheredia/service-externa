package com.exact.service.externa.dao;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.AreaPlazoDistribucion;

@Repository
public interface IAreaPlazoDistribucionDao extends CrudRepository<AreaPlazoDistribucion, Long> {
	
	@Query(value="SELECT plazo_id from area_plazo_distribucion where area_id = ?1", nativeQuery=true)
	public Map<String, Object> getPlazoDistribucionIdByAreaId(Long id);
	
	
}
