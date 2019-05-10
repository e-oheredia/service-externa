package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.SubambitoPlazoDistribucion;

@Repository
public interface ISubambitoPlazoDistribucionDao extends CrudRepository<SubambitoPlazoDistribucion, Long>{

	@Query(value="SELECT * FROM subambito_plazo_distribucion WHERE subambito_id=?1 AND plazo_distribucion_id=?2", nativeQuery=true)
	public SubambitoPlazoDistribucion getPlazoDistribucionBySubambitoId(Long id, Long plazoId);
	
}
