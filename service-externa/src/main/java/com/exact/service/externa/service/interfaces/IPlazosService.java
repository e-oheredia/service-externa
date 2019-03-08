package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IPlazosService {
	
	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException;
	public Map<String, Object> guardar(Map<String, Object> plazo);
}
