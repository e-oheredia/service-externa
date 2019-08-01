package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.exact.service.externa.entity.RegionPlazoDistribucion;

@Repository
public interface IRegionPlazoDistribucionDao extends CrudRepository<RegionPlazoDistribucion, Long>{

	@Query(value="SELECT * FROM region_plazo_distribucion WHERE region_id=?1 AND plazo_distribucion_id=?2", nativeQuery=true)
	public RegionPlazoDistribucion getPlazoDistribucionBySubambitoId(Long id, Long plazoId);
	
	@Query(value="SELECT * FROM region_plazo_distribucion WHERE region_id=?1", nativeQuery=true)
	public Iterable<RegionPlazoDistribucion> getPlazosDistribucionByRegion(Long id);	
	
	
	@Query("SELECT rp FROM RegionPlazoDistribucion rp WHERE rp.plazoDistribucion.id=?1")
	public Iterable<RegionPlazoDistribucion> listarRegionIds(Long regionplazoId);	
	
	
	@Transactional
	@Modifying
	@Query("DELETE FROM RegionPlazoDistribucion ap WHERE ap.plazoDistribucion.id=?1")
	void eliminarbyplazoid(Long plazoId);
	
	@Query(value="SELECT * FROM region_plazo_distribucion WHERE plazo_distribucion_id=?1", nativeQuery=true)
	public RegionPlazoDistribucion getRegionByPlazoID(Long plazoId);
	
}
