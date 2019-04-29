package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IAmbitoDiaService {

	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException, Exception; 
	
	public Iterable<Map<String, Object>> listardiaslaborales(Long id) throws IOException, JSONException, Exception; 
	
}
