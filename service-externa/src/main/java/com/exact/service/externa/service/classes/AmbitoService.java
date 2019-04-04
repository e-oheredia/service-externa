package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IAmbitoEdao;
import com.exact.service.externa.service.interfaces.IAmbitoService;

@Service
public class AmbitoService implements IAmbitoService{

	@Autowired
	IAmbitoEdao ambitoEdao;
	
	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException {
		return ambitoEdao.listarAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitos() throws IOException, JSONException {
		return ambitoEdao.listarSubAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosByAmbitoId(Long id) throws IOException, JSONException {
		return ambitoEdao.listarSubAmbitosActivosByAmbitoId(id);
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws IOException, JSONException {
		return ambitoEdao.listarSubAmbitosActivos();
	}

	@Override
	public Map<String, Object> guardarSubAmbito(String ambito) throws IOException, JSONException {
		return ambitoEdao.guardarSubAmbito(ambito);
	}

	@Override
	public Map<String, Object> modificarSubAmbito(Long id, String ambito) throws IOException, JSONException {
		return ambitoEdao.modificarSubAmbito(id, ambito);
	}

}
