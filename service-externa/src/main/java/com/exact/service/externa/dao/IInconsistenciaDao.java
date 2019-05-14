package com.exact.service.externa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Inconsistencia;

@Repository
public interface IInconsistenciaDao extends CrudRepository<Inconsistencia, Long>{

}
