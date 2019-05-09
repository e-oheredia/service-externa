package com.exact.service.externa.edao.interfaces;

import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import io.jsonwebtoken.io.IOException;

public interface IFeriadoEdao {
	
	public Map<String, Object> Eliminar(Long id) throws IOException, JSONException, Exception;

	Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException, java.io.IOException;
	
	public Map<String, Object> guardar(String feriado) throws IOException, JSONException, Exception;

}
