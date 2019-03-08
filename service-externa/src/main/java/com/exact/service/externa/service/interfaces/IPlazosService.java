package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IPlazosService {
	
	public Iterable<Map<String, Object>> listarAll() throws  IOException, JSONException;
	public Map<String, Object> guardar(String plazo) throws  IOException, JSONException;
	public Map<String, Object> modificar(Long id, String plazo) throws  IOException, JSONException;
}
