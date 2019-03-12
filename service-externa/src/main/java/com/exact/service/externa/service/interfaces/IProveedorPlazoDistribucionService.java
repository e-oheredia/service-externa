package com.exact.service.externa.service.interfaces;


import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.exact.service.externa.entity.ProveedorPlazoDistribucion;

public interface IProveedorPlazoDistribucionService {
	
	Iterable<Map<String, Object>> getPlazoPorProveedorId(Long id);
//	ProveedorPlazoDistribucion actualizar(ProveedorPlazoDistribucion proveedorPlazoDistribucion);
	ProveedorPlazoDistribucion guardarProveedorPlazos(Long proveedorId,JSONArray plazos) throws JSONException;
	ProveedorPlazoDistribucion actualizarProveedorPlazos(Long proveedorId,JSONArray plazos) throws JSONException;
}
