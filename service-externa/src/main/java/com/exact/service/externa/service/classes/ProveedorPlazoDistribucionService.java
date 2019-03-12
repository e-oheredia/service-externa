package com.exact.service.externa.service.classes;


import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IProveedorPlazoDistribucionDao;
import com.exact.service.externa.entity.ProveedorPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IProveedorPlazoDistribucionService;

@Service
public class ProveedorPlazoDistribucionService implements IProveedorPlazoDistribucionService{

	@Autowired
	IProveedorPlazoDistribucionDao proveedorPlazoDistribucionDao;
	
	@Override
	public Iterable<Map<String, Object>> getPlazoPorProveedorId(Long id) {
		return proveedorPlazoDistribucionDao.getPlazoDistribucionIdByProveedorId(id);
	}

//	@Override
//	public ProveedorPlazoDistribucion actualizar(ProveedorPlazoDistribucion proveedorPlazoDistribucion) {
//		if (proveedorPlazoDistribucionDao.existsById(proveedorPlazoDistribucion.getProveedorId())) {
//			return proveedorPlazoDistribucionDao.save(proveedorPlazoDistribucion);
//		}
//		return null;
//	}
	
	@Override
	public ProveedorPlazoDistribucion guardarProveedorPlazos(Long proveedorId, JSONArray plazos) throws JSONException {
		for(int i=0;i<plazos.length();i++) {
			ProveedorPlazoDistribucion proveedorPlazoDistribucion = new ProveedorPlazoDistribucion();
			JSONObject plazo = plazos.getJSONObject(i);
			proveedorPlazoDistribucion.setProveedorId(proveedorId);
			proveedorPlazoDistribucion.setPlazoId(plazo.getLong("id"));
			proveedorPlazoDistribucionDao.save(proveedorPlazoDistribucion);
		}
		return null;
	}

	@Override
	public ProveedorPlazoDistribucion actualizarProveedorPlazos(Long proveedorId, JSONArray plazos) throws JSONException {
		if(plazos==null) {
			return null;
		}
		proveedorPlazoDistribucionDao.eliminarPlazos(proveedorId);
		return guardarProveedorPlazos(proveedorId, plazos);
	}
}
