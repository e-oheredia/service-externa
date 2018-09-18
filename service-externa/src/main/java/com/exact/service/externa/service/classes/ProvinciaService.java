package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IProvinciaEdao;
import com.exact.service.externa.service.interfaces.IProvinciaService;

@Service
public class ProvinciaService implements IProvinciaService {
	
	@Autowired
	IProvinciaEdao provinciaEdao;

	@Override
	public Iterable<Map<String, Object>> listarProvinciasByDepartamentoId(Long departamentoId)
			throws ClientProtocolException, IOException, JSONException {
		return provinciaEdao.listarProvinciasByDepartamentoId(departamentoId);
	}

	@Override
	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException {
		return provinciaEdao.listarAll();
	}

}
