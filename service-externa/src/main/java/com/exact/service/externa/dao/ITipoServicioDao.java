package com.exact.service.externa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.TipoServicio;

@Repository
public interface ITipoServicioDao extends CrudRepository<TipoServicio, Long> {

}
