package com.exact.service.externa.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.Proveedor;


@Repository
public interface IProveedorDao extends CrudRepository<Proveedor,Long>{

	@Query(value=" Select p.proveedor_id From proveedor_plazo_distribucion As p where p.plazo_distribucion_id =?1", nativeQuery=true)	
	public List<BigInteger> finproveedorByPlazo(Long id);
	
}
