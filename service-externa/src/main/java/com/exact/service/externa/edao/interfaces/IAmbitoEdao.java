package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;



public interface IAmbitoEdao  {

	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarSubAmbitos() throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarSubAmbitosActivosByAmbitoId(Long id) throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws IOException, JSONException;
	public Map<String, Object> guardarSubAmbito(String ambito) throws ClientProtocolException, IOException, JSONException;
	public Map<String, Object> modificarSubAmbito(Long id, String ambito) throws IOException, JSONException;	  
	
}
