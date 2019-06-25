package com.exact.service.externa.service.interfaces;


import com.exact.service.externa.entity.PlazoDistribucion;

public interface IPlazoDistribucionService {
	
	public Iterable<PlazoDistribucion> listarPlazosActivos();
	public Iterable<PlazoDistribucion> listarByProveedorId(Long proveedorId);
	public PlazoDistribucion guardar(PlazoDistribucion plazodistribucion);
	public Iterable<PlazoDistribucion> listarAll() throws Exception;
	public PlazoDistribucion modificar(PlazoDistribucion plazodistribucion, Long ambitoId);
}
