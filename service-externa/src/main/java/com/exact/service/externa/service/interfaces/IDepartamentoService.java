package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IDepartamentoService {
	public Iterable<Map<String, Object>> listarDepartamentosByPaisId(Long paisId) throws ClientProtocolException, IOException, JSONException;
	Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException;
}
