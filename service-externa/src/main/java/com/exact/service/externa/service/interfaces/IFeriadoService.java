package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;


public interface IFeriadoService {
	Map<String, Object> eliminar(Long id)throws ClientProtocolException, IOException, JSONException, io.jsonwebtoken.io.IOException, Exception;
	
	public Map<String, Object> guardarferiado(Long id, String feriado) throws IOException, JSONException;

	public Iterable<Map<String, Object>> listarferiados() throws IOException, JSONException;
	
	
	public Map<String, Object> guardarferiados(String feriado) throws IOException, JSONException, io.jsonwebtoken.io.IOException, Exception;

		
}
