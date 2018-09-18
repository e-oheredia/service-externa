package com.exact.service.externa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Documento;

@Repository
public interface IDocumentoDao extends CrudRepository<Documento, Long> {
	
	@Query(value="SELECT TOP 1 documento_autogenerado FROM documento ORDER BY documento_id DESC", nativeQuery=true)
	public String getMaxDocumentoAutogenerado();
}
