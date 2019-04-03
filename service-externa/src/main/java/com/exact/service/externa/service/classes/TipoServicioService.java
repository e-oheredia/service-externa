package com.exact.service.externa.service.classes;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.List;

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

	@Override
	public TipoServicio guardar(TipoServicio tipoServicio) {
		if(tipoServicio.getNombre()==null) {
			return null;
		}
		return tipoServicioDao.save(tipoServicio);
	}

	@Override
	public Iterable<TipoServicio> listarTipoServicioActivos() {
		Iterable<TipoServicio> tipoServicioBD = tipoServicioDao.findAll();
		List<TipoServicio> tipoServiciolst = StreamSupport.stream(tipoServicioBD.spliterator(), false).collect(Collectors.toList());
		tipoServiciolst.removeIf(tiposervicio -> !tiposervicio.isActivo());
		return tipoServiciolst;
	}

}
