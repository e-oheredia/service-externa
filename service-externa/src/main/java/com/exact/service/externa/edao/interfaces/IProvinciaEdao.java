package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IProvinciaEdao {
	public Iterable<Map<String, Object>> listarProvinciasByDepartamentoId(Long departamentoId)
			throws IOException, JSONException;
	Iterable<Map<String, Object>> listarAll() throws IOException, JSONException;
}
