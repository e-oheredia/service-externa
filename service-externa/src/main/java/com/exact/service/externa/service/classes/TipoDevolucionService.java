package com.exact.service.externa.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.ITipoDevolucionDao;
import com.exact.service.externa.entity.TipoDevolucion;
import com.exact.service.externa.service.interfaces.ITipoDevolucionService;

@Service
public class TipoDevolucionService implements ITipoDevolucionService{

	@Autowired
	ITipoDevolucionDao tipodevolucionDao;
	
	@Override
	public Iterable<TipoDevolucion> listarAll() {
		return tipodevolucionDao.findAll();
	}

}
