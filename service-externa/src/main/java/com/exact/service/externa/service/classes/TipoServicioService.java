package com.exact.service.externa.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.ITipoServicioDao;
import com.exact.service.externa.entity.TipoServicio;
import com.exact.service.externa.service.interfaces.ITipoServicioService;

@Service
public class TipoServicioService implements ITipoServicioService {
	
	@Autowired
	ITipoServicioDao tipoServicioDao;
	
	@Override
	public Iterable<TipoServicio> listarAll() {
		return tipoServicioDao.findAll();
	}

}
