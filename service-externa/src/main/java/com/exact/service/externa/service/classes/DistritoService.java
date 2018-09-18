package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.service.interfaces.IDistritoService;

@Service
public class DistritoService implements IDistritoService {
	
	@Autowired
	IDistritoEdao distritoEdao;
	
	@Override
	public Iterable<Map<String, Object>> listarDistritosByIdProvincia(Long provinciaId)
			throws ClientProtocolException, IOException, JSONException {
		return distritoEdao.listarDistritosByIdProvincia(provinciaId);
	}

	@Override
	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException {
		return distritoEdao.listarAll();
	}

}
