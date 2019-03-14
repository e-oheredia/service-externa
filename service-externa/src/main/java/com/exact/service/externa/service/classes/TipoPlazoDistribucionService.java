package com.exact.service.externa.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.ITipoPlazoDistribucionDao;
import com.exact.service.externa.entity.TipoPlazoDistribucion;
import com.exact.service.externa.service.interfaces.ITipoPlazoDistribucionService;

@Service
public class TipoPlazoDistribucionService implements ITipoPlazoDistribucionService{

	@Autowired
	ITipoPlazoDistribucionDao tipoPlazoDao;
	
	@Override
	public Iterable<TipoPlazoDistribucion> listarAll() {
		return tipoPlazoDao.findAll();
	}

}
