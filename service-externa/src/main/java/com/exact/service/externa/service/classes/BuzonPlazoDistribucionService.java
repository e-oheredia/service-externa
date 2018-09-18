package com.exact.service.externa.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IBuzonPlazoDistribucionDao;
import com.exact.service.externa.entity.BuzonPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IBuzonPlazoDistribucionService;

@Service
public class BuzonPlazoDistribucionService implements IBuzonPlazoDistribucionService {
	
	@Autowired
	IBuzonPlazoDistribucionDao buzonPlazoDistribucionDao;
	
	@Override
	public BuzonPlazoDistribucion listarById(Long id) {
		return buzonPlazoDistribucionDao.findById(id).orElse(null);
	}

}
