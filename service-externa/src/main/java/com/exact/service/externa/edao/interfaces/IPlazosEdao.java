package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;


public interface IPlazosEdao {

	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException;
	public Map<String, Object> guardar(Map<String, Object> plazo);
}
