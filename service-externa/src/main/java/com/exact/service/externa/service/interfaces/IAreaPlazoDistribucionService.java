package com.exact.service.externa.service.interfaces;

import java.util.Map;

import com.exact.service.externa.entity.AreaPlazoDistribucion;

public interface IAreaPlazoDistribucionService {
	Map<String, Object> listarById(Long id);
	AreaPlazoDistribucion actualizar(AreaPlazoDistribucion areaPlazoDistribucion);
}
