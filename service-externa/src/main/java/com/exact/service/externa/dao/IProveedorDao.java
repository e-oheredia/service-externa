package com.exact.service.externa.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Proveedor;

@Repository
public interface IProveedorDao extends CrudRepository<Proveedor,Long>{

}
