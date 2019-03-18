package com.exact.service.externa.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.ITipoSeguridadDao;
import com.exact.service.externa.entity.TipoSeguridad;
import com.exact.service.externa.service.interfaces.ITipoSeguridadService;

@Service
public class TipoSeguridadService implements ITipoSeguridadService {
	
	@Autowired
	ITipoSeguridadDao tipoSeguridadDao;
	
	@Override
	public Iterable<TipoSeguridad> listarAll() {
		return tipoSeguridadDao.findAll();
	}

	@Override
	public TipoSeguridad guardar(TipoSeguridad tiposeguridad) {
		if(tiposeguridad.getNombre()==null) {
			return null;
		}
		return tipoSeguridadDao.save(tiposeguridad);
	}

}
