package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IProvinciaService {
	public Iterable<Map<String, Object>> listarProvinciasByDepartamentoId(Long departamentoId) throws ClientProtocolException, IOException, JSONException;
	Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException;
}
