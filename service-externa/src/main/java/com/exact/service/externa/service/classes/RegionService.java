package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IRegionEdao;
import com.exact.service.externa.service.interfaces.IRegionService;

@Service
public class RegionService implements IRegionService{

	@Autowired
	IRegionEdao RegionEdao;	
	
	@Override
	public Iterable<Map<String, Object>> listarRegiones() throws Exception {
		return RegionEdao.listarAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborales(Long ambitoid) throws IOException, JSONException, Exception {
		return RegionEdao.listardiaslaborales(ambitoid);
	}

	@Override
	public Iterable<Map<String, Object>> listarhoraslaborales(Long ambitoid) throws ClientProtocolException, IOException, JSONException {
		return RegionEdao.listarhoraslaborales(ambitoid);

	}

	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException {
		return RegionEdao.listarSubAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listarAmbitosByAmbitoId(Long id) throws IOException, JSONException {
		return RegionEdao.listarSubAmbitosActivosByAmbitoId(id);

	}

	@Override
	public Iterable<Map<String, Object>> listarAmbitosActivos() throws IOException, JSONException {
		return RegionEdao.listarSubAmbitosActivos();
	}

	@Override
	public Map<String, Object> guardarAmbito(String ambito) throws IOException, JSONException {
		return RegionEdao.guardarSubAmbito(ambito);

	}

	@Override
	public Map<String, Object> modificarAmbito(Long id, String ambito) throws IOException, JSONException {
		return RegionEdao.modificarSubAmbito(id, ambito);

	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborables() throws IOException, JSONException, Exception {
		return RegionEdao.listarAmbitos();

	}

	@Override
	public Map<String, Object> modificarRegion(Long id, String ambito) throws IOException, JSONException {
		return RegionEdao.modificarAmbito(id, ambito);

	}

}
