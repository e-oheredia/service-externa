package com.exact.service.externa.service.interfaces;

import com.exact.service.externa.entity.PlazoDistribucion;

public interface IPlazoDistribucionService {
	
	public Iterable<PlazoDistribucion> listarAll();
	public Iterable<PlazoDistribucion> listarByProveedorId(Long proveedorId);
	public PlazoDistribucion guardar(PlazoDistribucion plazodistribucion);
}
