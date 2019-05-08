package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IAmbitoDiasEdao;
import com.exact.service.externa.edao.interfaces.IAmbitoEdao;
import com.exact.service.externa.service.interfaces.IAmbitoService;

@Service
public class AmbitoService implements IAmbitoService{

	@Autowired
	IAmbitoEdao ambitoEdao;
	
	@Autowired
	IAmbitoDiasEdao ambitoEdaos;

	
	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws Exception {
		return ambitoEdaos.listarAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitos() throws IOException, JSONException {
		return ambitoEdaos.listarSubAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosByAmbitoId(Long id) throws IOException, JSONException {
		return ambitoEdaos.listarSubAmbitosActivosByAmbitoId(id);
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws IOException, JSONException {
		return ambitoEdaos.listarSubAmbitosActivos();
	}

	@Override
	public Map<String, Object> guardarSubAmbito(String ambito) throws IOException, JSONException {
		return ambitoEdaos.guardarSubAmbito(ambito);
	}

	@Override
	public Map<String, Object> modificarSubAmbito(Long id, String ambito) throws IOException, JSONException {
		return ambitoEdaos.modificarSubAmbito(id, ambito);
	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborables() throws Exception {
		return ambitoEdaos.listarAmbitos();
	}

	@Override
	public Map<String, Object> modificarAmbito(Long id, String ambito) throws IOException, JSONException {
		return ambitoEdaos.modificarAmbito(id, ambito);
	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborales(Long id) throws IOException, JSONException, Exception {
		return ambitoEdaos.listardiaslaborales(id) ;
	}

	@Override
	public Iterable<Map<String, Object>> listarhoraslaborales(Long id) throws IOException, JSONException, Exception {
		return ambitoEdaos.listarhoraslaborales(id) ;
	}

}
