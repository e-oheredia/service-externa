package com.exact.service.externa.service.classes;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAreaPlazoDistribucionDao;
import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IAreaPlazoDistribucionService;

@Service
public class AreaPlazoDistribucionService implements IAreaPlazoDistribucionService {
	@Autowired
	IAreaPlazoDistribucionDao areaPlazoDistribucionDao;
	
	@Override
	public AreaPlazoDistribucion listarById(Long id) {
		return areaPlazoDistribucionDao.getPlazoDistribucionIdByAreaId(id);
	}
	
	@Override
	public AreaPlazoDistribucion actualizar(AreaPlazoDistribucion areaPlazoDistribucion) {
		if (areaPlazoDistribucionDao.existsById(areaPlazoDistribucion.getAreaId())) {
			return areaPlazoDistribucionDao.save(areaPlazoDistribucion);
		}
		return null;
	}

}
