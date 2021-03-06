package com.exact.service.externa.edao.interfaces;

import java.util.Map;

import org.json.JSONException;

import io.jsonwebtoken.io.IOException;

public interface IProductoEdao {
	 
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException, Exception;
	public Map<String, Object> guardar(String producto) throws IOException, JSONException, Exception;
	public Map<String, Object> modificar(Long id, String producto) throws IOException, JSONException, Exception;
	public Iterable<Map<String, Object>> listarActivos() throws IOException, JSONException, Exception;
}
