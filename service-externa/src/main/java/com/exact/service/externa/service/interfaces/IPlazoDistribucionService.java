package com.exact.service.externa.service.interfaces;


import java.io.IOException;

import org.json.JSONException;

import com.exact.service.externa.entity.PlazoDistribucion;

public interface IPlazoDistribucionService {
	
	public Iterable<PlazoDistribucion> listarPlazosActivos();
	public Iterable<PlazoDistribucion> listarByProveedorId(Long proveedorId);
	public PlazoDistribucion guardar(PlazoDistribucion plazodistribucion);
	public Iterable<PlazoDistribucion> listarAll() throws Exception;
	public PlazoDistribucion modificar(PlazoDistribucion plazodistribucion);
	public Iterable<PlazoDistribucion> listarPlazosByRegionId(Long regionId);
	public Iterable<PlazoDistribucion> listarPlazosByDistritoId(Long distritoId)throws IOException, JSONException;
}
