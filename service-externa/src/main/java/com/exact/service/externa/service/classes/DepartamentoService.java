package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IDepartamentoEdao;
import com.exact.service.externa.service.interfaces.IDepartamentoService;
@Service
public class DepartamentoService implements IDepartamentoService {
	
	@Autowired
	IDepartamentoEdao departamentoEdao;

	@Override
	public Iterable<Map<String, Object>> listarDepartamentosByPaisId(Long paisId)
			throws ClientProtocolException, IOException, JSONException {
		return departamentoEdao.listarDepartamentosByPaisId(paisId);
	}

	@Override
	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException {
		return departamentoEdao.listarAll();
	}

}
