package com.exact.service.externa.service.classes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;

@Service
public class PlazoDistribucionService implements IPlazoDistribucionService {
	
	@Autowired
	IPlazoDistribucionDao plazoDistribucionDao;
	
	@Override
	public Iterable<PlazoDistribucion> listarPlazosActivos() {
		Iterable<PlazoDistribucion> plazosBD = plazoDistribucionDao.findAll();
		List<PlazoDistribucion> plazoslst = StreamSupport.stream(plazosBD.spliterator(), false).collect(Collectors.toList());
		plazoslst.removeIf(plazo -> !plazo.isActivo());
		return plazoslst;
	}

	@Override
	public Iterable<PlazoDistribucion> listarByProveedorId(Long proveedorId) {
		return plazoDistribucionDao.findByProveedorId(proveedorId);
	}

	@Override
	public PlazoDistribucion guardar(PlazoDistribucion plazodistribucion) {
		if(plazodistribucion.getNombre()==null) {
			return null;
		}
		return plazoDistribucionDao.save(plazodistribucion);
	}

	@Override
	public Iterable<PlazoDistribucion> listarAll() {
		return plazoDistribucionDao.findAll();
	}

}
