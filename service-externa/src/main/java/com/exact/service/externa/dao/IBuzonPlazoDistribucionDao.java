package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.BuzonPlazoDistribucion;

@Repository
public interface IBuzonPlazoDistribucionDao extends CrudRepository<BuzonPlazoDistribucion, Long> {
	
	@Query(value="SELECT plazo_distribucion_id from buzon_plazo_distribucion where buzon_id = ?1", nativeQuery=true)
	public Long getPlazoDistribucionIdByBuzonId(Long id);
	
}
