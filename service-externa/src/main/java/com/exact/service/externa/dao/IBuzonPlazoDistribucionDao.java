package com.exact.service.externa.dao;

import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.entity.BuzonPlazoDistribucion;

@Repository
public interface IBuzonPlazoDistribucionDao extends CrudRepository<BuzonPlazoDistribucion, Long> {
	
	@Query(value="SELECT plazo_id from buzon_plazo_distribucion where buzon_id = ?1", nativeQuery=true)
	public BuzonPlazoDistribucion getPlazoDistribucionIdByBuzonId(Long id);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM BuzonPlazoDistribucion bp WHERE bp.buzonId = ?1")
	public void eliminarPlazos(Long id);
}
