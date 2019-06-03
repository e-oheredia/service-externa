package com.exact.service.externa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Documento;



@Repository
public interface IReporteDao extends CrudRepository<Documento, Long>{

}
