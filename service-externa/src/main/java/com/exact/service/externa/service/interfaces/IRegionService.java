package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IRegionService {

	public Iterable<Map<String, Object>> listarRegiones() throws IOException, JSONException, Exception; 
	
	public Iterable<Map<String, Object>> listardiaslaborales(Long id) throws IOException, JSONException, Exception;
	
	public Iterable<Map<String, Object>> listarhoraslaborales(Long id) throws IOException, JSONException, Exception;
	
	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException, Exception;
	
	public Iterable<Map<String, Object>> listarAmbitosByAmbitoId(Long id) throws IOException, JSONException;
	
	public Iterable<Map<String, Object>> listarAmbitosActivos() throws IOException, JSONException;
	
	public Map<String, Object> guardarAmbito(String ambito) throws IOException, JSONException;
	
	public Map<String, Object> modificarAmbito(Long id, String ambito) throws IOException, JSONException;

	public Iterable<Map<String, Object>> listardiaslaborables() throws IOException, JSONException, Exception;
	
	public Map<String, Object> modificarRegion(Long id, String ambito) throws IOException, JSONException;
	
	public Iterable<Map<String, Object>> RegionesbyProveedor(Long id) throws IOException, JSONException;
	
}
