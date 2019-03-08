package com.exact.service.externa.service.classes;

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
	public Iterable<PlazoDistribucion> listarAll() {
		return plazoDistribucionDao.findAll();
	}

	@Override
	public Iterable<PlazoDistribucion> listarByProveedorId(Long proveedorId) {
		return plazoDistribucionDao.findByProveedorId(proveedorId);
	}

	@Override
	public PlazoDistribucion guardar(PlazoDistribucion plazoDistribucion) {
		return plazoDistribucionDao.save(plazoDistribucion);
		
	}

}
