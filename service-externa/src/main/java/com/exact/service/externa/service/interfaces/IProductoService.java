package com.exact.service.externa.service.interfaces;

import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import io.jsonwebtoken.io.IOException;

public interface IProductoService {
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException, Exception;
	public Map<String, Object> guardar(String producto) throws IOException, JSONException, Exception;
	public Map<String, Object> modificar(Long id, String producto) throws IOException, JSONException, Exception;
	public Iterable<Map<String, Object>> listarActivos() throws IOException, JSONException, Exception;
}
