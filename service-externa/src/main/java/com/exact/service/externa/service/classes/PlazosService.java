package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IPlazosEdao;
import com.exact.service.externa.service.interfaces.IPlazosService;

@Service
public class PlazosService implements IPlazosService {

	@Autowired
	IPlazosEdao plazosEdao;
	
	@Override
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException {
		return plazosEdao.listarAll();
	}

	@Override
	public Map<String, Object> guardar(String plazo) throws IOException, JSONException {
		return plazosEdao.guardar(plazo);
	}

	@Override
	public Map<String, Object> modificar(Long id, String plazo) throws IOException, JSONException {
		return plazosEdao.modificar(id, plazo);
	}

}
