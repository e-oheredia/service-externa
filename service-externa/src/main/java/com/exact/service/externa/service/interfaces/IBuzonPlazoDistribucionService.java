package com.exact.service.externa.service.interfaces;

import java.util.Map;

import com.exact.service.externa.entity.BuzonPlazoDistribucion;

public interface IBuzonPlazoDistribucionService {
	
	BuzonPlazoDistribucion listarById(Long id);

	BuzonPlazoDistribucion actualizar(BuzonPlazoDistribucion buzonPlazoDistribucion);
}
