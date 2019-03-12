package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IProveedorEdao {

	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException;
	public Map<String, Object> guardar(String proveedor) throws ClientProtocolException, IOException, JSONException;
	public Map<String, Object> modificar(Long id, String proveedor) throws ClientProtocolException, IOException, JSONException;
	
}
