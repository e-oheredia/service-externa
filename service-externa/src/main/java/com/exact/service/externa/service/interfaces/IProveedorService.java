package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;



public interface IProveedorService {

//	Iterable<Proveedor> listarProveedores() throws ClientProtocolException, IOException, JSONException;
	
	public Iterable<Map<String, Object>> listarAll() throws  IOException, JSONException;
	public Map<String, Object> guardarProveedor(String proveedor) throws  IOException, JSONException;
	public Map<String, Object> modificar(Long id, String proveedor) throws  IOException, JSONException;
	
}
