package com.exact.service.externa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.MotivoEstado;
@Repository
public interface IMotivoEstadoDao extends CrudRepository<MotivoEstado, Long> {

	
}
