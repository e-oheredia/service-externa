package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IAmbitoService {

	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarSubAmbitos() throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarSubAmbitosByAmbitoId(Long id) throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws IOException, JSONException;
	public Map<String, Object> guardarSubAmbito(String ambito) throws IOException, JSONException;
	public Map<String, Object> modificarSubAmbito(Long id, String ambito) throws IOException, JSONException;
	
}